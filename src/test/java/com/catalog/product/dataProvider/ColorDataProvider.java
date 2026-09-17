package com.catalog.product.dataProvider;

import com.catalog.product.controller.dto.ColorDTO;
import com.catalog.product.controller.dto.ColorDTOResponse;
import com.catalog.product.persistence.ColorEntity;

public class ColorDataProvider {
    public static ColorEntity createColorTest(){
        return ColorEntity.builder()
                .id(1L)
                .color("azul")
                .build();
    }
    public static ColorDTO createColorDTOTest(){
        return new ColorDTO("azul");
    }
    public static ColorDTOResponse createColorDTOResponseTest(){
        return new ColorDTOResponse(1L,"azul");
    }
}
