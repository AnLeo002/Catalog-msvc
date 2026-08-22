package com.catalog.category.service;

import com.catalog.category.controller.dtos.CategoryDTO;
import com.catalog.category.controller.dtos.CategoryDTOResponse;

import java.util.Set;

public interface CategoryService {
    CategoryDTOResponse findByCategory(String category);
    CategoryDTOResponse findById(Long id);
    Set<CategoryDTOResponse> findAll();
    CategoryDTOResponse createCategory(CategoryDTO categoryDTO);
    CategoryDTOResponse updateCategory(CategoryDTO categoryDTO, Long id);
    void deleteCategoryById(Long id);
}
