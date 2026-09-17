package com.catalog.product.dataProvider;


import com.catalog.product.controller.dto.BrandDTO;
import com.catalog.product.controller.dto.BrandDTOResponse;
import com.catalog.product.persistence.BrandEntity;

public class BrandDataProvider {
    public static BrandEntity createBrandTest(){
        return BrandEntity.builder()
                .id(1L)
                .brand("nike")
                .build();
    }
    public static BrandDTO createBrandDTOTest(){
        return new BrandDTO("nike");
    }
    public static BrandDTOResponse createBrandDTOResponseTest(){
        return new BrandDTOResponse(1L,"nike");
    }
}
