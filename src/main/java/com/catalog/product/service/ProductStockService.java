package com.catalog.product.service;

import com.catalog.product.controller.dto.ProductStockDTO;
import com.catalog.product.controller.dto.ProductStockDTOResponse;
import com.catalog.product.persistence.ProductStockEntity;

import java.util.Set;

public interface ProductStockService {
    Set<ProductStockEntity> createStockWithProduct (Set<ProductStockDTO> productStockDTO);
    ProductStockDTOResponse createStock (ProductStockDTO productStockDTO);
    ProductStockDTOResponse updateStock (ProductStockDTO productStockDTO);
    Set<ProductStockDTOResponse> updateStockList(Set<ProductStockDTO> productStockDTOS);
    void deleteStock (ProductStockDTO productStockDTO);


}
