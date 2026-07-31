package com.catalog.product.controller;

import com.catalog.product.controller.dto.SizeDTO;
import com.catalog.product.controller.dto.SizeDTOResponse;
import com.catalog.product.service.ISizeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/size")
public class SizeController {
    public final ISizeService service;

    public SizeController(ISizeService service) {
        this.service = service;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<SizeDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findSizeById(id));
    }
    @GetMapping("/findSize/{size}")
    public ResponseEntity<SizeDTOResponse> findByName(@PathVariable String size){
        return ResponseEntity.ok(service.findSizeBySize(size));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<SizeDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<SizeDTOResponse> createSize(@RequestBody @Valid SizeDTO sizeDTO){
        return new ResponseEntity<>(service.createSize(sizeDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<SizeDTOResponse> updateSize(@PathVariable Long id,@RequestBody @Valid SizeDTO sizeDTO){
        return ResponseEntity.ok(service.updateSize(sizeDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSize(@PathVariable Long id){
        service.deleteSizeById(id);
        return new ResponseEntity<>("La talla fue eliminada correctamente",HttpStatus.NO_CONTENT);
    }
}
