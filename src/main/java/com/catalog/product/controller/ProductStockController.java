package com.catalog.product.controller;


import com.catalog.product.controller.dto.ProductSizeIdDTO;
import com.catalog.product.controller.dto.ProductStockDTO;
import com.catalog.product.controller.dto.ProductStockDTOResponse;
import com.catalog.product.service.IProductStockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@RequestMapping("/stock")
public class ProductStockController {
    public final IProductStockService service;

    public ProductStockController(IProductStockService service) {
        this.service = service;
    }

    @GetMapping("/find")
    public ResponseEntity<ProductStockDTOResponse> findById(@RequestBody @Valid ProductSizeIdDTO id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/findAll")
    public ResponseEntity<Set<ProductStockDTOResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<ProductStockDTOResponse> createStock(@RequestBody @Valid ProductStockDTO productStockDTO){
        return new ResponseEntity<>(service.createStock(productStockDTO), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<ProductStockDTOResponse> updateStock(@RequestBody @Valid ProductStockDTO productStockDTO){
        return ResponseEntity.ok(service.updateStock(productStockDTO));
    }
    @PutMapping("/update/Set")
    public ResponseEntity<Set<ProductStockDTOResponse>> updateProductAllStock(@RequestBody @Valid Set<ProductStockDTO> productStockDTOS){
        return ResponseEntity.ok(service.updateStockList(productStockDTOS));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody @Valid ProductSizeIdDTO productSizeIdDTO){
        service.deleteStock(productSizeIdDTO);
        return new ResponseEntity<>("El stock fue eliminado correctamente",HttpStatus.NO_CONTENT);
    }
}
