package com.catalog.product.controller.dto;

import com.catalog.product.persistence.primaryKey.ProductSizeId;
import jakarta.validation.constraints.NotBlank;

public record ProductStockDTOResponse(
        @NotBlank ProductSizeId id,
        @NotBlank Long productId,
        @NotBlank Long sizeId,
        @NotBlank Integer stock
) {
}
