package com.catalog.category.service.impl;

import com.catalog.category.service.CategoryService;
import com.catalog.category.controller.dtos.CategoryDTO;
import com.catalog.category.controller.dtos.CategoryDTOResponse;
import com.catalog.category.persistence.CategoryEntity;
import com.catalog.category.repo.CategoryRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo repo;

    public CategoryServiceImpl(CategoryRepo repo) {
        this.repo = repo;
    }

    @Override
    public CategoryDTOResponse findByCategory(String category) {
        CategoryEntity categoryEntity = repo.findByCategoryIgnoreCase(category)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La categoría no se encuentra en la base de datos"));
        return new CategoryDTOResponse(categoryEntity.getId(),categoryEntity.getCategory());
    }

    @Override
    public CategoryDTOResponse findById(Long id) {
        CategoryEntity categoryEntity = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La categoría no se encuentra en la base de datos"));
        return new CategoryDTOResponse(categoryEntity.getId(),categoryEntity.getCategory());
    }

    @Override
    public Set<CategoryDTOResponse> findAll() {
        List<CategoryEntity> categoryEntities = repo.findAll();
        if (categoryEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT,"La base de datos se encuentra vacia");
        return categoryEntities.stream()
                .map(c-> new CategoryDTOResponse(c.getId(),c.getCategory()))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public CategoryDTOResponse createCategory(CategoryDTO categoryDTO) {
        if (repo.findByCategoryIgnoreCase(categoryDTO.category()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"La categoría ya se encuentra en la base de datos");
        CategoryEntity category = CategoryEntity.builder()
                .category(categoryDTO.category())
                .build();
        CategoryEntity categoryCreated = repo.save(category);
        return new CategoryDTOResponse(categoryCreated.getId(),categoryCreated.getCategory());
    }

    @Override
    @Transactional
    public CategoryDTOResponse updateCategory(CategoryDTO categoryDTO, Long id) {
        CategoryEntity categoryEntity = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La categoría no se encuentra en la base de datos"));
        if (repo.existsByCategoryIgnoreCaseAndIdNot(categoryDTO.category(),id))
            throw new ResponseStatusException(HttpStatus.CONFLICT,"La categoría ya existe");
        categoryEntity.setCategory(categoryDTO.category());
        repo.save(categoryEntity);
        return new CategoryDTOResponse(categoryEntity.getId(),categoryEntity.getCategory());
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        if (!repo.findById(id).isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La categoría no puede ser eliminada por que no existe");
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"La categoría no puede ser eliminada");
        }
    }
}
