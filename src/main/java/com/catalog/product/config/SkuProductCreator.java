package com.catalog.product.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class SkuProductCreator {
    private final ValidationEntities validationEntities;
    public String createSku(ProductComponentsValidatedResponse components, String name, Optional<Long> id){
        if (validationEntities.validateProductAlreadyExists(components,name,id))
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ya existe un producto registrado con este nombre, marca, tipo, género y color.");
        String n = validateLengthSku(name);
        String b = validateLengthSku(components.brand().getBrand());
        String c = validateLengthSku(components.color().getColor());
        String g = validateLengthSku(components.gender().getGender());
        String t = validateLengthSku(components.type().getType());
        return ( n+ "-" +b + "-" +c+ "-" +g+ "-" +t).toUpperCase().replaceAll("\\s+", "");
    }
    public String validateLengthSku(String cod){
        String cleanCod = cod.trim();
        if (cleanCod.length() >=3) {
            return cleanCod.substring(0, 3);
        }
        return cleanCod;
    }
}
