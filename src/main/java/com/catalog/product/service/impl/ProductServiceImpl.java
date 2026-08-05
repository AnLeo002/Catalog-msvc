package com.catalog.product.service.impl;

import com.catalog.product.config.SkuProductCreator;
import com.catalog.product.config.ProductComponentsValidatedResponse;
import com.catalog.product.config.ValidationEntities;
import com.catalog.product.service.IProductService;

import com.catalog.product.controller.dto.*;
import com.catalog.product.persistence.ProductEntity;
import com.catalog.product.persistence.ProductStockEntity;
import com.catalog.product.persistence.primaryKey.ProductSizeId;
import com.catalog.product.repo.ProductRepo;
import com.catalog.product.repo.ProductStockRepo;
import com.catalog.product.repo.SizeRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // Importante para que los cambios se guarden solos
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepo repo;
    private final ModelMapper modelMapper;
    private final ProductStockServiceImpl productStockService;
    private final SizeRepo sizeRepo;
    private final ProductStockRepo productStockRepo;
    private final ValidationEntities validationEntities;
    private final SkuProductCreator skuCreator;

    @Override
    @Transactional
    public ProductDTOResponse createProduct(ProductDTO productDTO) {
        ProductComponentsValidatedResponse productComponentsValidated = validationEntities.validateProductComponents(productDTO);
        String sku = skuCreator.createSku(productComponentsValidated,productDTO.name(),Optional.empty());
        ProductEntity product = ProductEntity.builder()
                .sku(sku)
                .name(productDTO.name())
                .color(productComponentsValidated.color())
                .type(productComponentsValidated.type())
                .gender(productComponentsValidated.gender())
                .description(productDTO.description())
                .price(productDTO.price())
                .brand(productComponentsValidated.brand())
                .build();
        if (productDTO.images() != null && !productDTO.images().isEmpty()){
            product.setImageUrls(productDTO.images());
        }
        try{
            repo.save(product);
            if (productDTO.stockDTOS() != null && !productDTO.stockDTOS().isEmpty()) {
                Set<ProductStockEntity> productStock = productStockService.createStockWithProduct(productDTO.stockDTOS(),product.getId());
                product.setProductStockEntities(productStock);
            }
            return modelMapper.map(product, ProductDTOResponse.class);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el producto",e);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public ProductDTOResponse findProductById(Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "el producto no se encuentra en la base de datos"));
        return modelMapper.map(product,ProductDTOResponse.class);

    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTOResponse findProductByName(String name) {
        ProductEntity product = repo.findByNameIgnoreCase(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra en la base de datos"));
        return modelMapper.map(product,ProductDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTOResponse> findAll() {
        List<ProductEntity> productEntities = repo.findAll();
        if (productEntities.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La base de datos se encuentra vacia");
        }
        return  productEntities.stream()
                .map(product -> modelMapper.map(product,ProductDTOResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public ProductDTOResponse updateProductNoStock(ProductDTO productDTO, Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        ProductComponentsValidatedResponse components = validationEntities.validateProductComponents(productDTO);
        String sku = skuCreator.createSku(components,productDTO.name(),Optional.of(id));
        // 3. Actualización selectiva (Evita sobreescribir con nulos)
        product.setSku(sku);
        if (!product.getName().equalsIgnoreCase(productDTO.name())) product.setName(productDTO.name());
        if (!product.getDescription().equalsIgnoreCase(productDTO.description())) product.setDescription(productDTO.description());
        if (product.getPrice().compareTo(productDTO.price()) != 0) product.setPrice(productDTO.price());

        if (!product.getBrand().getId().equals(components.brand().getId())) product.setBrand(components.brand());
        if (!product.getColor().getId().equals(components.color().getId())) product.setColor(components.color());
        if (!product.getType().getId().equals(components.type().getId())) product.setType(components.type());
        if (!product.getGender().getId().equals(components.gender().getId())) product.setGender(components.gender());

        // No hace falta repo.save(product) si usas @Transactional,
        return modelMapper.map(product, ProductDTOResponse.class);
    }

    @Override
    @Transactional
    public Set<ProductStockDTOResponse> updateAllStock(Set<ProductStockUpdateDTO> productStockUpdateDTOS, Long id) {

        if (productStockUpdateDTOS.isEmpty())throw new ResponseStatusException(HttpStatus.NO_CONTENT,"El listado para actualización se encuentra vacío");
        Set<ProductStockEntity> productStock = repo.findProductStock(id);

        Set<ProductStockEntity> toUpdate = new HashSet<>();
        Set<ProductStockEntity> toCreate = new HashSet<>();

        Map<Long, ProductStockEntity> currentMap = productStock.stream()
                .collect(Collectors.toMap(e -> e.getId().getSizeId(), e -> e));
        // 2. Recorremos lo que llega del DTO
        for (ProductStockUpdateDTO incoming : productStockUpdateDTOS) {
            ProductStockEntity existing = currentMap.get(incoming.sizeId());

            if (existing != null) {
                if (!existing.getStock().equals(incoming.stock())) {
                    existing.setStock(incoming.stock());
                    toUpdate.add(existing);
                }
            } else {
                ProductStockEntity newEntity = ProductStockEntity.builder()
                        .id(new ProductSizeId(id, incoming.sizeId()))
                        .stock(incoming.stock())
                        .size(sizeRepo.getReferenceById(incoming.sizeId()))
                        .product(repo.getReferenceById(id))
                        .build();
                toCreate.add(newEntity);
            }
        }
        if (!toUpdate.isEmpty())productStockRepo.saveAll(toUpdate);
        if (!toCreate.isEmpty())productStockRepo.saveAll(toCreate);

        Set<ProductStockEntity> finalOrderedSet = new HashSet<>(toUpdate);
        finalOrderedSet.addAll(toCreate);
        return finalOrderedSet.stream()
                .map(entity -> new ProductStockDTOResponse(entity.getId(),entity.getStock()))
                .collect(Collectors.toSet());
    }
    @Override
    @Transactional
    public ProductImagesDTOResponse updateImages(ProductImagesDTO images, Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se encuentra en la base de datos"));
        product.getImageUrls().clear();
        if (images.images() != null && !images.images().isEmpty()) {
            product.getImageUrls().addAll(images.images());
        }
        return new ProductImagesDTOResponse(product.getImageUrls());
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        if (!repo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se encuentra en la base de datos para ser eliminado");
        }
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"No se puede eliminar el producto porque tiene registros relacionados");
        }
    }
}
