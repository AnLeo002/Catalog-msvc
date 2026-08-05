package com.catalog.product.service.impl;

import com.catalog.product.controller.dto.ProductCreateStockDTO;
import com.catalog.product.controller.dto.ProductSizeIdDTO;
import com.catalog.product.service.IProductStockService;
import com.catalog.product.controller.dto.ProductStockDTO;
import com.catalog.product.controller.dto.ProductStockDTOResponse;
import com.catalog.product.persistence.ProductEntity;
import com.catalog.product.persistence.ProductStockEntity;
import com.catalog.product.persistence.SizeEntity;
import com.catalog.product.persistence.primaryKey.ProductSizeId;
import com.catalog.product.repo.ProductRepo;
import com.catalog.product.repo.ProductStockRepo;
import com.catalog.product.repo.SizeRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductStockServiceImpl implements IProductStockService {
    private final ProductStockRepo repo;
    private final ProductRepo productRepo;
    private final SizeRepo sizeRepo;

    public ProductStockServiceImpl(ProductStockRepo repo, ProductRepo productRepo, SizeRepo sizeRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.sizeRepo = sizeRepo;
    }

    @Override
    public ProductStockDTOResponse findById(ProductSizeIdDTO productSizeIdDTO) {
        ProductStockEntity stock = repo.findById(new ProductSizeId(productSizeIdDTO.productId(), productSizeIdDTO.sizeId()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El stock no se encuentra en la base de datos"));
        return new ProductStockDTOResponse(stock.getId(),stock.getStock());
    }

    @Override
    public Set<ProductStockDTOResponse> findAll() {
        List<ProductStockEntity> productStockEntities = repo.findAll();
        if(productStockEntities.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"No se encuentra nada en el stock");
        }
        return productStockEntities.stream()
                .map(stock -> new ProductStockDTOResponse(stock.getId(), stock.getStock()))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional // Asegura que si falla uno, no se guarde ninguno
    public Set<ProductStockEntity> createStockWithProduct(Set<ProductCreateStockDTO> dtoSet, Long id) {
        // 1. Extraer todos los ID para buscarlos de golpe (Optimización N+1)
        Set<Long> sizeIds = dtoSet.stream().map(ProductCreateStockDTO::sizeId).collect(Collectors.toSet());
        // 2. Cargar mapas de referencia (Consultas masivas)
        ProductEntity product = productRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se encuentra en la base de datos"));
        Map<Long, SizeEntity> sizes = sizeRepo.findAllById(sizeIds).stream()
                .collect(Collectors.toMap(SizeEntity::getId, s -> s));
        List<ProductStockEntity> toSave = new ArrayList<>();
        // 3. Procesar y validar
        for (ProductCreateStockDTO dto : dtoSet) {
            SizeEntity s = sizes.get(dto.sizeId());

            if (product == null || s == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto o Talla no existen");

            ProductSizeId stockId = new ProductSizeId(product.getId(), dto.sizeId());

            toSave.add(ProductStockEntity.builder()
                    .id(stockId).product(product).size(s).stock(dto.stock())
                    .build());
        }
        // 4. Guardar en un solo envío all
        List<ProductStockEntity> saved = repo.saveAll(toSave);
        // 5. Retornar ORDENADO alfabéticamente (Uso de LinkedHashSet para mantener el orden)
        return saved.stream()
                .sorted(Comparator.comparing(e -> e.getSize().getSize()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    @Transactional
    public ProductStockDTOResponse createStock(ProductStockDTO productStockDTO) {

        ProductEntity product = productRepo.findById(productStockDTO.productId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se encuentra en la base de datos"));
        SizeEntity size = sizeRepo.findById(productStockDTO.sizeId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La talla no se encuentra en la base de datos"));

        ProductSizeId productSizeId = new ProductSizeId(productStockDTO.productId(), productStockDTO.sizeId());
        if (repo.existsById(productSizeId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Este producto ya contiene esta talla");
        }
        ProductStockEntity productStock = ProductStockEntity.builder()
                .id(productSizeId)
                .product(product)
                .size(size)
                .stock(productStockDTO.stock())
                .build();
         try {
             ProductStockEntity productSave = repo.save(productStock);
             return new ProductStockDTOResponse(productSave.getId(), productSave.getStock());
         }catch (DataIntegrityViolationException e) {
             // Excepción específica de Spring para errores de BD (llaves foráneas, etc)
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de integridad al crear el stock", e);
         }
    }

    @Override
    @Transactional
    public ProductStockDTOResponse updateStock(ProductStockDTO productStockDTO) {
        if (!productRepo.existsById(productStockDTO.productId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se encuentra en la base de datos");
        if (!sizeRepo.existsById(productStockDTO.sizeId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La talla no se encuentra en la base de datos");
        //Esta petición a base de datos permite controlar el stock para que no existan discrepancias
        int result = repo.updateStockAtomic(productStockDTO.productId(), productStockDTO.sizeId(), productStockDTO.stock());
        if (result == 0){
            ProductSizeId id = new ProductSizeId(productStockDTO.productId(), productStockDTO.sizeId());
            if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El stock no se encuentra en la base de datos");

            throw new ResponseStatusException(HttpStatus.CONFLICT,"El stock no puede ser actualizado");
        }
        ProductStockEntity productSave = repo.findById(new ProductSizeId(productStockDTO.productId(), productStockDTO.sizeId()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock no encontrado"));
        return new ProductStockDTOResponse(productSave.getId(), productSave.getStock());
    }
    @Override
    @Transactional
    public Set<ProductStockDTOResponse> updateStockList(Set<ProductStockDTO> productStockDTOS) {
        // 1. Extraer todos los ID para buscarlos de golpe (Optimización N+1)
        Set<Long> productIds = productStockDTOS.stream().map(ProductStockDTO::productId).collect(Collectors.toSet());
        Set<Long> sizeIds = productStockDTOS.stream().map(ProductStockDTO::sizeId).collect(Collectors.toSet());

        // 2. Cargar mapas de referencia (Consultas masivas)
        Map<Long, ProductEntity> products = productRepo.findAllById(productIds).stream()
                .collect(Collectors.toMap(ProductEntity::getId, p -> p));
        Map<Long, SizeEntity> sizes = sizeRepo.findAllById(sizeIds).stream()
                .collect(Collectors.toMap(SizeEntity::getId, s -> s));

        Set<ProductSizeId> toSave = new HashSet<>();

        // 3. Procesar y validar
        for (ProductStockDTO dto : productStockDTOS) {
            ProductEntity p = products.get(dto.productId());
            SizeEntity s = sizes.get(dto.sizeId());

            if (p == null || s == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto o Talla no existen");
            ProductSizeId id = new ProductSizeId(dto.productId(), dto.sizeId());
            int result = repo.updateStockAtomic(dto.productId(), dto.sizeId(), dto.stock());
            if (result == 0){
                if (!repo.existsById(id)){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El stock no se encuentra en la base de datos");
                }
                throw new ResponseStatusException(HttpStatus.CONFLICT,"El stock no puede ser actualizado");
            }
            toSave.add(id);
        }
        return repo.findAllById(toSave).stream()
                .map(pro -> new ProductStockDTOResponse(pro.getId(), pro.getStock()))
                .collect(Collectors.toSet());
    }
    @Override
    @Transactional
    public void deleteStock(ProductSizeIdDTO productSizeIdDTO) {
        ProductSizeId id = new ProductSizeId(productSizeIdDTO.productId(), productSizeIdDTO.sizeId());
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No es posible eliminar el stock por que no se encuentra en base de datos");
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El stock no pudo ser eliminado");
        }
    }


}
