package com.catalog.service.controller;

import com.catalog.service.controller.dto.ServiceDTO;
import com.catalog.service.controller.dto.ServiceDTOResponse;
import com.catalog.service.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@RestController
@RequestMapping("service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService service;
    @GetMapping("/find/{id}")
    public ResponseEntity<ServiceDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/findService/{s}")
    public ResponseEntity<ServiceDTOResponse> findByService(@PathVariable String s){
        return ResponseEntity.ok(service.findByService(s));
    }
    @GetMapping("/findAll")
    public ResponseEntity<Set<ServiceDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<ServiceDTOResponse> createService(@RequestBody @Valid ServiceDTO serviceDTO){
        return new ResponseEntity<>(service.createService(serviceDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceDTOResponse> updateService(@RequestBody @Valid ServiceDTO serviceDTO,@PathVariable Long id){
        return ResponseEntity.ok(service.updateService(serviceDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        service.deleteService(id);
        return new ResponseEntity<>("El contenido fue eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}
