package com.catalog.service.controller;

import com.catalog.service.controller.dto.ContentDTO;
import com.catalog.service.controller.dto.ContentDTOResponse;
import com.catalog.service.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@RestController
@RequestMapping("content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService service;
    @GetMapping("/find/{id}")
    public ResponseEntity<ContentDTOResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/findContent/{content}")
    public ResponseEntity<ContentDTOResponse> findByContent(@PathVariable String content){
        return ResponseEntity.ok(service.findByContent(content));
    }
    @GetMapping("/findAll")
    public ResponseEntity<Set<ContentDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<ContentDTOResponse> createContent(@RequestBody @Valid ContentDTO contentDTO){
        return new ResponseEntity<>(service.createContent(contentDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ContentDTOResponse> updateContent(@RequestBody @Valid ContentDTO contentDTO,@PathVariable Long id){
        return ResponseEntity.ok(service.updateContent(contentDTO,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable Long id) {
        service.deleteContent(id);
        return new ResponseEntity<>("El contenido fue eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}
