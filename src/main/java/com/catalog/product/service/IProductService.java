package com.catalog.product.service;

import com.catalog.product.controller.dto.*;

import java.util.List;
import java.util.Set;

public interface IProductService {
    ProductDTOResponse createProduct(ProductDTO productDTO);
    ProductDTOResponse findProductById(Long id);
    ProductDTOResponse findProductByName(String name);
    List<ProductDTOResponse> findAll();
    ProductDTOResponse updateProductNoStock(ProductDTO productDTO,Long id);
    Set<ProductStockDTOResponse> updateAllStock(Set<ProductStockUpdateDTO> productStockUpdateDTOS, Long id);
    ProductImagesDTOResponse updateImages(ProductImagesDTO images, Long id);
    void deleteProductById(Long id);



}
