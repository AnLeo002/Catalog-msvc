package com.catalog.config.product;

import com.catalog.category.persistence.CategoryEntity;
import com.catalog.category.repo.CategoryRepo;
import com.catalog.product.controller.dto.ProductDTO;
import com.catalog.product.persistence.BrandEntity;
import com.catalog.product.persistence.ColorEntity;
import com.catalog.product.persistence.GenderEntity;
import com.catalog.product.persistence.TypeEntity;
import com.catalog.product.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductValidationEntities {

    private final ColorRepo colorRepo;
    private final TypeRepo typeRepo;
    private final GenderRepo genderRepo;
    private final BrandRepo brandRepo;
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductComponentsValidatedResponse validateProductComponents (ProductDTO productDTO){
        BrandEntity brand =  brandRepo.findByBrandIgnoreCase(productDTO.brand())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La marca no se encuentra en la base de datos"));
        ColorEntity color = colorRepo.findByIdColorIgnoreCase(productDTO.color())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El color no se encuentra en la base de datos"));
        TypeEntity type = typeRepo.findByTypeIgnoreCase(productDTO.type())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El tipo no se encuentra en la base de datos"));
        GenderEntity gender = genderRepo.findByGenderIgnoreCase(productDTO.gender())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El genero no se encuentra en la base de datos"));
        CategoryEntity category = categoryRepo.findByCategoryIgnoreCase(productDTO.category())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La categoría no se encuentra en la base de datos"));
        return new ProductComponentsValidatedResponse(color,type,gender,brand,category);
    }
    public boolean validateProductAlreadyExists(ProductComponentsValidatedResponse components, String name, Optional<Long> id){
       return productRepo.existsByUniqueAttributes(
                name.trim(),
                components.brand().getId(),
                components.type().getId(),
                components.gender().getId(),
                components.color().getId(),
                id.orElse(null)
        );
    }
}
