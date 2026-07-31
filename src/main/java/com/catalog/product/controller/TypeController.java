package com.catalog.product.controller;

import com.catalog.product.controller.dto.TypeDTO;
import com.catalog.product.controller.dto.TypeDTOResponse;
import com.catalog.product.service.ITypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
public class TypeController {
    private final ITypeService service;
    @GetMapping("/find/{id}")
    public ResponseEntity<TypeDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/find/type/{type}")
    public ResponseEntity<TypeDTOResponse> findByName(@PathVariable String type){
        return ResponseEntity.ok(service.findByType(type));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<TypeDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<TypeDTOResponse> createType(@Valid @RequestBody TypeDTO typeDTO){
        return new ResponseEntity<>(service.createType(typeDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<TypeDTOResponse> updateType(@PathVariable Long id,@Valid @RequestBody TypeDTO typeDTO){
        return ResponseEntity.ok(service.updateType(typeDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id){
        service.deleteType(id);
        return new ResponseEntity<>("El tipo fue eliminado correctamente",HttpStatus.NO_CONTENT);
    }
}
