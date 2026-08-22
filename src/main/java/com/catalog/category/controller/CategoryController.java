package com.catalog.category.controller;

import com.catalog.category.service.CategoryService;
import com.catalog.category.controller.dtos.CategoryDTO;
import com.catalog.category.controller.dtos.CategoryDTOResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<CategoryDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/findCategory/{category}")
    public ResponseEntity<CategoryDTOResponse> findByCategory(@PathVariable String category){
        return ResponseEntity.ok(service.findByCategory(category));
    }
    @GetMapping("/findAll")
    public ResponseEntity<Set<CategoryDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<CategoryDTOResponse> createCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return new ResponseEntity<>(service.createCategory(categoryDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTOResponse> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO,@PathVariable Long id){
        return ResponseEntity.ok(service.updateCategory(categoryDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        service.deleteCategoryById(id);
        return new ResponseEntity<>("La talla fue eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}
