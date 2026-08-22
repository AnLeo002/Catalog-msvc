package com.catalog.product.controller.dto;

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
                                 Set<ProductStockDTOResponse> productStock,
                                 @NotBlank String category,
                                 List<String> images
                                 ) {
}
