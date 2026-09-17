package com.catalog.product.dataProvider;

import com.catalog.product.controller.dto.ProductCreateStockDTO;
import com.catalog.product.controller.dto.ProductStockDTO;
import com.catalog.product.controller.dto.ProductStockDTOResponse;
import com.catalog.product.controller.dto.StockDTOResponse;
import com.catalog.product.persistence.ProductStockEntity;
import com.catalog.product.persistence.primaryKey.ProductSizeId;

public class ProductStockDataProvider {
    public static ProductSizeId createProductSizeIdTest(){
        return new ProductSizeId(1L,1L);
    }
    public static ProductStockEntity createProductStockTest(){
        return ProductStockEntity.builder()
                .id(createProductSizeIdTest())
                .product(ProductDataProvider.createProductTest())
                .size(SizeDataProvider.createSizeTest())
                .stock(20)
                .build();
    }
    public static ProductStockDTO createProductStockDTOTest(){
        return new ProductStockDTO(1L,1L,20);
    }
    public static ProductStockDTOResponse createProductStockDTOResponseTest(){
        return new ProductStockDTOResponse(createProductSizeIdTest(),20);
    }
    public static ProductCreateStockDTO createProductCreateStockDTOTest(){
        return new ProductCreateStockDTO(1L,20);
    }
    public static StockDTOResponse createStockDTOResponseTest(){
        return new StockDTOResponse("L",20);
    }
}
