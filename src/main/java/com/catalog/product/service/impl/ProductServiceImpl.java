package com.catalog.product.service.impl;

import com.catalog.product.service.ProductService;

import com.catalog.product.controller.dto.*;
import com.catalog.product.persistence.BrandEntity;
import com.catalog.product.persistence.ProductEntity;
import com.catalog.product.persistence.ProductStockEntity;
import com.catalog.product.persistence.primaryKey.ProductSizeId;
import com.catalog.product.repo.BrandRepo;
import com.catalog.product.repo.ProductRepo;
import com.catalog.product.repo.ProductStockRepo;
import com.catalog.product.repo.SizeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional // Importante para que los cambios se guarden solos
public class ProductServiceImpl implements ProductService {
    private final ProductRepo repo;
    private final ModelMapper modelMapper;
    private final BrandRepo brandRepo;
    private final ProductStockServiceImpl productStockService;
    private final SizeRepo sizeRepo;
    private final ProductStockRepo productStockRepo;

    public ProductServiceImpl(ProductRepo repo, ModelMapper modelMapper, BrandRepo brandRepo, ProductStockServiceImpl productStockService, SizeRepo sizeRepo, ProductStockRepo productStockRepo) {
        this.repo = repo;
        this.modelMapper = modelMapper;
        this.brandRepo = brandRepo;
        this.productStockService = productStockService;
        this.sizeRepo = sizeRepo;
        this.productStockRepo = productStockRepo;
    }


    @Override
    public ProductDTOResponse createProduct(ProductDTO productDTO) {
        if(repo.findByNameIgnoreCase(productDTO.name()).isPresent()){
          throw new ResponseStatusException(HttpStatus.CONFLICT, productDTO.name()+" ya se encuentra en la base de datos");
        }
        BrandEntity brand = brandRepo.findByBrandIgnoreCase(productDTO.brandDTO().brand())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, productDTO.brandDTO().brand() + "no se encuentra en la base de datos"));

        Set<ProductStockEntity> productStock = productStockService.createStockWithProduct(productDTO.productStockDTO());
        ProductEntity product = ProductEntity.builder()
                .name(productDTO.name())
                .color(productDTO.color())
                .description(productDTO.description())
                .price(productDTO.price())
                .brand(brand)
                .build();
        try{
            ProductEntity productSave = repo.save(product);
            if (productStock.isEmpty()){
                return modelMapper.map(productSave, ProductDTOResponse.class);
            }
            productSave.setProductStockEntities(productStock);

            productSave = repo.save(productSave);
            return modelMapper.map(productSave, ProductDTOResponse.class);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el producto",e);
        }
    }
    @Override
    public ProductDTOResponse findProductById(Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "el producto no se encuentra en la base de datos"));
        return modelMapper.map(product,ProductDTOResponse.class);

    }

    @Override
    public ProductDTOResponse findProductByName(String name) {
        ProductEntity product = repo.findByNameIgnoreCase(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra en la base de datos"));
        return modelMapper.map(product,ProductDTOResponse.class);
    }

    @Override
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
    public ProductDTOResponse updateProductNoStock(ProductDTO productDTO, Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (productDTO.brandDTO() != null && !product.getBrand().getBrand().equalsIgnoreCase(productDTO.brandDTO().brand())) {
            BrandEntity brand = brandRepo.findByBrandIgnoreCase(productDTO.brandDTO().brand())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));
            product.setBrand(brand);
        }

        // 3. Actualización selectiva (Evita sobreescribir con nulos)
        if (productDTO.name() != null && !product.getName().equalsIgnoreCase(productDTO.name())) product.setName(productDTO.name());
        if (productDTO.color() != null && !product.getColor().equalsIgnoreCase(productDTO.color())) product.setColor(productDTO.color());
        if (productDTO.description() != null && !product.getDescription().equalsIgnoreCase(productDTO.description())) product.setDescription(productDTO.description());
        if (productDTO.price() != null && !product.getPrice().equals(productDTO.price())) product.setPrice(productDTO.price());

        // No hace falta repo.save(product) si usas @Transactional,
        return modelMapper.map(product, ProductDTOResponse.class);
    }

    @Override
    public Set<ProductStockSetDTOResponse> updateAllStock(ProductStockSetDTO productStockSetDTO, Long id) {
        Set<ProductStockEntity> productStock = repo.findProductStock(id);

        if (productStock.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"El producto no contiene stock");
        }
        Set<ProductStockEntity> toUpdate = new HashSet<>();
        Set<ProductStockEntity> toCreate = new HashSet<>();

        Map<Long, ProductStockEntity> currentMap = productStock.stream()
                .collect(Collectors.toMap(e -> e.getId().getSizeId(), e -> e));
        // 2. Recorremos lo que llega del DTO
        for (ProductStockDTO incoming : productStockSetDTO.productStockDTOS()) {
            ProductStockEntity existing = currentMap.get(incoming.sizeId());

            if (existing != null) {
                // EXISTE: Solo si el stock cambió, lo agregamos a la lista de updates
                if (!existing.getStock().equals(incoming.stock())) {
                    existing.setStock(incoming.stock()+existing.getStock());
                    toUpdate.add(existing);
                }
            } else {
                // NO EXISTE: Lo preparamos para crear
                ProductStockEntity newEntity = ProductStockEntity.builder()
                        .id(new ProductSizeId(id, incoming.sizeId()))
                        .stock(incoming.stock())
                        .size(sizeRepo.getReferenceById(incoming.sizeId()))
                        .product(repo.getReferenceById(id))
                        .build();
                toCreate.add(newEntity);
            }
        }
        // 3. Persistimos los cambios
        productStockRepo.saveAll(toUpdate);
        productStockRepo.saveAll(toCreate);
        // 5. CONVERTIR A SET manteniendo el orden para el DTO
        // El LinkedHashSet respeta el orden en que metes los elementos (el orden del sortedResult)
        Set<ProductStockEntity> finalOrderedSet = toUpdate;
        finalOrderedSet.addAll(toCreate);
        // 6. MAPEAR A RESPUESTA
        return finalOrderedSet.stream()
                .map(entity -> new ProductStockSetDTOResponse(Collections.singleton(new ProductStockDTOResponse(entity.getId(), entity.getProduct().getId(), entity.getSize().getId(), entity.getStock()))))
                .collect(Collectors.toSet());
    }

    @Override
    public ProductStockSetDTOResponse createAllStock(ProductStockSetDTO productStockSetDTO, Long id) {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }
}
