package com.catalog.config;

import com.catalog.category.controller.dtos.CategoryDTOResponse;
import com.catalog.product.controller.dto.ProductDTOResponse;
import com.catalog.product.controller.dto.StockDTOResponse;
import com.catalog.product.persistence.ProductEntity;
import com.catalog.service.controller.dto.ContentDTOResponse;
import com.catalog.service.controller.dto.ServiceDTOResponse;
import com.catalog.service.persistence.ServiceEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ProductEntity.class, ProductDTOResponse.class)
                .setConverter(productToDTOConverter());

        modelMapper.createTypeMap(ServiceEntity.class, ServiceDTOResponse.class)
                .setConverter(serviceToDTOConverter());

        return modelMapper;
    }
    private Converter<ProductEntity, ProductDTOResponse> productToDTOConverter() {
        return context -> {
            ProductEntity source = context.getSource();

            Set<StockDTOResponse> productStockDTOResponseList = null;
            if (source.getProductStockEntities() != null) {
                productStockDTOResponseList = source.getProductStockEntities().stream()
                        .map(productStockEntity -> new StockDTOResponse(
                                productStockEntity.getSize().getSize(),
                                productStockEntity.getStock()
                        ))
                        .collect(Collectors.toSet());
            }
            List<CategoryDTOResponse> categoryDTOResponseList = null;
            if (source.getCategories() != null) {
                categoryDTOResponseList = source.getCategories().stream()
                        .map(category -> new CategoryDTOResponse(category.getId(), category.getCategory()))
                        .toList();
            }

            return new ProductDTOResponse(
                    source.getId(),
                    source.getSku(),
                    source.getName(),
                    source.getColor().getColor(),
                    source.getType().getType(),
                    source.getGender().getGender(),
                    source.getPrice(),
                    source.getDescription(),
                    source.getBrand().getBrand() != null ? source.getBrand().getBrand() : "N/A",
                    productStockDTOResponseList,
                    categoryDTOResponseList,
                    source.getImageUrls()
            );
        };
    }
    private Converter<ServiceEntity, ServiceDTOResponse> serviceToDTOConverter() {
        return context -> {
            ServiceEntity source = context.getSource();

            List<CategoryDTOResponse> categoryDTOResponseList = null;
            if (source.getCategoryEntities() != null) {
                categoryDTOResponseList = source.getCategoryEntities().stream()
                        .map(category -> new CategoryDTOResponse(category.getId(), category.getCategory()))
                        .toList();
            }
            List<ContentDTOResponse> contentDTOResponseList = null;
            if (source.getContentEntities() != null) {
                contentDTOResponseList = source.getContentEntities().stream()
                        .map(content -> new ContentDTOResponse(content.getId(), content.getContent()))
                        .toList();
            }

            return new ServiceDTOResponse(
                    source.getId(),
                    source.getService(),
                    source.getDescription(),
                    source.getPrice(),
                    categoryDTOResponseList,
                    contentDTOResponseList
            );
        };
    }
}
