package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProductDTOResponse(@NotBlank Long id,
                                 @NotBlank String name,
                                 @NotBlank String color,
                                 @NotBlank String price,
                                 @NotBlank String description,
                                 @NotBlank String brand,
                                 @NotBlank List<ProductStockDTOResponse> productStock
                                 ) {
}
