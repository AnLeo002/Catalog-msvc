package com.catalog.category;

import com.catalog.category.controller.dtos.CategoryDTO;
import com.catalog.category.controller.dtos.CategoryDTOResponse;
import com.catalog.category.persistence.CategoryEntity;

public class CategoryDataProvider {
    public static CategoryEntity createCategoryTest(){
        return CategoryEntity.builder()
                .id(1L)
                .category("Deportiva")
                .build();
    }
    public static CategoryDTO createCategoryDTOTest(){
        return new CategoryDTO("Deportiva");
    }
    public static CategoryDTOResponse createCategoryDTOResponseTest(){
        return new CategoryDTOResponse(1L,"Deportivo");
    }
}
