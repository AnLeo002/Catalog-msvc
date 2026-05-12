package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTOResponse(@NotBlank Long id,
                                 @NotBlank String name,
                                 @NotBlank String color,
                                 @NotNull BigDecimal price,
                                 @NotBlank String description,
                                 @NotBlank String brand,
                                 @NotBlank List<ProductStockDTOResponse> productStock
                                 ) {
}
