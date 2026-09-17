package com.catalog.product.dataProvider;

import com.catalog.product.controller.dto.GenderDTO;
import com.catalog.product.controller.dto.GenderDTOResponse;
import com.catalog.product.persistence.GenderEntity;

public class GenderDataProvider {
    public static GenderEntity createGenderTest(){
        return GenderEntity.builder()
                .id(1L)
                .gender("unisex")
                .build();
    }
    public static GenderDTO createGenderDTOTest(){
        return new GenderDTO("unisex");
    }
    public static GenderDTOResponse createGenderDTOResponseTest(){
        return new GenderDTOResponse(1L,"unisex");
    }
}
