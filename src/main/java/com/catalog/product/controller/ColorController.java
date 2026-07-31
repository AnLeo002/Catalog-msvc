package com.catalog.product.controller;

import com.catalog.product.controller.dto.ColorDTO;
import com.catalog.product.controller.dto.ColorDTOResponse;
import com.catalog.product.service.IColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
public class ColorController {
    private final IColorService service;
    @GetMapping("/find/{id}")
    public ResponseEntity<ColorDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/find/color/{color}")
    public ResponseEntity<ColorDTOResponse> findByName(@PathVariable String color){
        return ResponseEntity.ok(service.findByColor(color));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<ColorDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<ColorDTOResponse> createColor(@Valid @RequestBody ColorDTO colorDTO){
        return new ResponseEntity<>(service.createColor(colorDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ColorDTOResponse> updateColor(@PathVariable Long id,@Valid @RequestBody ColorDTO colorDTO){
        return ResponseEntity.ok(service.updateColor(colorDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteColor(@PathVariable Long id){
        service.deleteColor(id);
        return new ResponseEntity<>("El color fue eliminado correctamente",HttpStatus.NO_CONTENT);
    }
}
