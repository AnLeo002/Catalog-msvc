package com.catalog.product.dataProvider;

import com.catalog.product.controller.dto.TypeDTO;
import com.catalog.product.controller.dto.TypeDTOResponse;
import com.catalog.product.persistence.TypeEntity;

public class TypeDataProvider {
    public static TypeEntity createTypeTest(){
        return TypeEntity.builder()
                .id(1L)
                .type("casual")
                .build();
    }
    public static TypeDTO createTypeDTOTest(){
        return new TypeDTO("casual");
    }
    public static TypeDTOResponse createTypeDTOResponseTest(){
        return new TypeDTOResponse(1L,"casual");
    }
}
