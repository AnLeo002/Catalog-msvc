package com.catalog.product.controller;

import com.catalog.product.controller.dto.GenderDTO;
import com.catalog.product.controller.dto.GenderDTOResponse;
import com.catalog.product.service.IGenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/gender")
@RequiredArgsConstructor
public class GenderController {
    private final IGenderService service;
    @GetMapping("/find/{id}")
    public ResponseEntity<GenderDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/find/gender/{gender}")
    public ResponseEntity<GenderDTOResponse> findByName(@PathVariable String gender){
        return ResponseEntity.ok(service.findByGender(gender));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<GenderDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<GenderDTOResponse> createGender(@Valid @RequestBody GenderDTO genderDTO){
        return new ResponseEntity<>(service.createGender(genderDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<GenderDTOResponse> updateGender(@Valid @PathVariable Long id, @RequestBody GenderDTO genderDTO){
        return ResponseEntity.ok(service.updateGender(genderDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGender(@PathVariable Long id){
        service.deleteGender(id);
        return new ResponseEntity<>("El genero fue eliminado correctamente",HttpStatus.NO_CONTENT);
    }
}
