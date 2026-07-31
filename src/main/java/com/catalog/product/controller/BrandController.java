package com.catalog.product.controller;

import com.catalog.product.controller.dto.BrandDTO;
import com.catalog.product.controller.dto.BrandDTOResponse;
import com.catalog.product.service.IBrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final IBrandService service;

    public BrandController(IBrandService service) {
        this.service = service;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<BrandDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findBrandById(id));
    }
    @GetMapping("/findName/{brand}")
    public ResponseEntity<BrandDTOResponse> findByBrand(@PathVariable String brand){
        return ResponseEntity.ok(service.findBrandByName(brand));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<BrandDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<BrandDTOResponse> createBrand(@RequestBody @Valid BrandDTO brandDTO){
        return new ResponseEntity<>(service.createBrand(brandDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<BrandDTOResponse> updateBrand(@RequestBody @Valid BrandDTO brandDTO,@PathVariable Long id){
        return ResponseEntity.ok(service.updateBrand(brandDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id){
        service.deleteBrandById(id);
        return new ResponseEntity<>("La talla fue eliminada correctamente",HttpStatus.NO_CONTENT);
    }
}
