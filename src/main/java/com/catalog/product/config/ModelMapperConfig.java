package com.catalog.product.config;

import com.catalog.product.controller.dto.ProductDTOResponse;
import com.catalog.product.controller.dto.ProductStockDTOResponse;
import com.catalog.product.persistence.ProductEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Converter<ProductEntity, ProductDTOResponse> productToDTOConvert = context -> {
            ProductEntity source = context.getSource();
            Set<ProductStockDTOResponse> productStockDTOResponseList = null;
            if (source.getProductStockEntities() != null){
                productStockDTOResponseList = source.getProductStockEntities().stream()
                        .map(productStockEntity -> new ProductStockDTOResponse(
                                productStockEntity.getId(),
                                productStockEntity.getProduct().getId(),
                                productStockEntity.getSize().getId(),
                                productStockEntity.getStock()
                        ))
                        .collect(Collectors.toSet());
            }
            return new ProductDTOResponse(
                    source.getId(),
                    source.getName(),
                    source.getColor(),
                    source.getPrice(),
                    source.getDescription(),
                    source.getBrand().getBrand() != null ? source.getBrand().getBrand() : "N/A",
                    productStockDTOResponseList
            );
        };
        TypeMap<ProductEntity, ProductDTOResponse> typeMap = modelMapper.createTypeMap(ProductEntity.class, ProductDTOResponse.class);
        typeMap.setConverter(productToDTOConvert);

        return modelMapper;
    }
}
