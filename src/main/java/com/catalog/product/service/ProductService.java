package com.catalog.product.service;

import com.catalog.product.controller.dto.ProductDTOResponse;
import com.catalog.product.controller.dto.ProductDTO;
import com.catalog.product.controller.dto.ProductStockSetDTO;
import com.catalog.product.controller.dto.ProductStockSetDTOResponse;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductDTOResponse createProduct(ProductDTO productDTO);
    ProductDTOResponse findProductById(Long id);
    ProductDTOResponse findProductByName(String name);
    List<ProductDTOResponse> findAll();
    ProductDTOResponse updateProductNoStock(ProductDTO productDTO,Long id);
    Set<ProductStockSetDTOResponse> updateAllStock(ProductStockSetDTO productStockSetDTO, Long id);
    ProductStockSetDTOResponse createAllStock(ProductStockSetDTO productStockSetDTO, Long id);
    void deleteProductById(Long id);



}
