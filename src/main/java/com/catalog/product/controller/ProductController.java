package com.catalog.product.controller;

import com.catalog.product.controller.dto.*;
import com.catalog.product.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService service;

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findProductById(id));
    }
    @GetMapping("/findName/{name}")
    public ResponseEntity<ProductDTOResponse> findByName(@PathVariable String name){
        return ResponseEntity.ok(service.findProductByName(name));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<ProductDTOResponse> createProduct(@Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(service.createProduct(productDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTOResponse> updateProductNoStock(@PathVariable Long id,@Valid @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(service.updateProductNoStock(productDTO, id));
    }
    @PutMapping("/update/images/{id}")
    public ResponseEntity<ProductImagesDTOResponse> updateImages(@PathVariable Long id, @RequestBody ProductImagesDTO dto){
        return ResponseEntity.ok(service. updateImages(dto,id));
    }
    @PutMapping("/update/stock/{id}")
    public ResponseEntity<Set<ProductStockDTOResponse>> updateProductAllStock(@Valid @RequestBody Set<ProductStockUpdateDTO> productStockUpdateDTOS, @PathVariable Long id){
        return ResponseEntity.ok(service.updateAllStock(productStockUpdateDTOS, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        service.deleteProductById(id);
        return new ResponseEntity<>("El producto fue eliminado correctamente",HttpStatus.NO_CONTENT);
    }
}
