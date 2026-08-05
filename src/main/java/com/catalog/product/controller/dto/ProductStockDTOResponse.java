package com.catalog.product.controller.dto;

import com.catalog.product.persistence.primaryKey.ProductSizeId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductStockDTOResponse(
        @NotBlank ProductSizeId id,
        @NotNull Integer stock
) {
}
