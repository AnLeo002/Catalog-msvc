package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record ProductDTO(@NotBlank String name,
                         @NotBlank String color,
                         @NotNull BigDecimal price,
                         @NotBlank String description,
                         @NotBlank BrandDTO brandDTO,
                         @NotBlank Set<ProductStockDTO> productStockDTO) {
}
