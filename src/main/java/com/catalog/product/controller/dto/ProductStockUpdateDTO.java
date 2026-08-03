package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ProductStockUpdateDTO(
        @NotNull Long sizeId,
        @NotNull Integer stock
) {
}
