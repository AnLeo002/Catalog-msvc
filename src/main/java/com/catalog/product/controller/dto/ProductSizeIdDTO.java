package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ProductSizeIdDTO(
        @NotNull Long productId,
        @NotNull Long sizeId
) {
}
