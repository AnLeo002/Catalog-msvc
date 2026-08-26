package com.catalog.product.controller.dto;

import com.catalog.category.controller.dtos.CategoryDTOResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ProductDTOResponse(@NotNull Long id,
                                 @NotBlank String sku,
                                 @NotBlank String name,
                                 @NotBlank String color,
                                 @NotBlank String type,
                                 @NotBlank String gender,
                                 @NotNull BigDecimal price,
                                 @NotBlank String description,
                                 @NotBlank String brand,
                                 Set<StockDTOResponse> stock,
                                 @NotBlank List<CategoryDTOResponse> categories,
                                 List<String> images
                                 ) {
}
