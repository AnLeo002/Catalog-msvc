package com.catalog.config.service;

import com.catalog.category.persistence.CategoryEntity;
import com.catalog.category.repo.CategoryRepo;
import com.catalog.service.persistence.ContentEntity;
import com.catalog.service.repo.ContentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceValidationEntities {
    private final ContentRepo contentRepo;
    private final CategoryRepo categoryRepo;
    public List<ContentEntity> validationContents(List<Long> contentIds){
        List<ContentEntity> contents = contentRepo.findAllById(contentIds);
        if (contents.size() != contentIds.size())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uno o más contenidos no existen en la base de datos");
        return contents;
    }
    public List<CategoryEntity> validationCategories(List<Long> categoryIds){
        List<CategoryEntity> categories = categoryRepo.findAllById(categoryIds);
        if (categories.size()!= categoryIds.size()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Uno o más contenidos no existen en la base de datos");
        return categories;
    }
}
