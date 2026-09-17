package com.catalog.product.dataProvider;

import com.catalog.product.controller.dto.SizeDTO;
import com.catalog.product.controller.dto.SizeDTOResponse;
import com.catalog.product.persistence.SizeEntity;

public class SizeDataProvider {
    public static SizeEntity createSizeTest(){
        return SizeEntity.builder()
                .id(1L)
                .size("L")
                .build();
    }
    public static SizeDTO createSizeDTOTest(){
        return new SizeDTO("L");
    }
    public static SizeDTOResponse createSizeDTOResponseTest(){
        return new SizeDTOResponse(1L,"L");
    }
}
