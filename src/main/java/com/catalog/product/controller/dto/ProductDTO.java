package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ProductDTO(@NotBlank String name,
                         @NotBlank String color,
                         @NotNull BigDecimal price,
                         @NotBlank String description,
                         @NotBlank String brand,
                         @NotBlank String type,
                         @NotBlank String gender,
                         Set<ProductCreateStockDTO> stockDTOS,
                         @NotEmpty List<Long> categories,
                         List<String> images) {
}
