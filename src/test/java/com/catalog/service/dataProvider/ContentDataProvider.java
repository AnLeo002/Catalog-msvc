package com.catalog.service.dataProvider;

import com.catalog.service.controller.dto.ContentDTO;
import com.catalog.service.controller.dto.ContentDTOResponse;
import com.catalog.service.persistence.ContentEntity;

public class ContentDataProvider {
    public static ContentEntity createContentTest(){
         return ContentEntity.builder()
                .id(1L)
                .content("100 fotos")
                .build();

    }
    public static ContentDTO createContentDTOTest(){
        return new ContentDTO("100 fotos");
    }
    public static ContentDTOResponse createContentDTOResponseTest(){
        return new ContentDTOResponse(1L,"100 fotos");
    }
}
