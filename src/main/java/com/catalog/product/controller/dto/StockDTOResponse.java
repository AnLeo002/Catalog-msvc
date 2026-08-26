package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record StockDTOResponse(
        @NotBlank String size,
        @NotBlank Integer stock
) {
}
