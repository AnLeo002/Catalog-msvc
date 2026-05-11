package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ProductStockDTO(
        @NotNull Long productId,
        @NotNull Long sizeId,
        @NotNull Integer stock
) {
}
