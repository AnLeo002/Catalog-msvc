package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ProductCreateStockDTO(
        @NotNull Long sizeId,
        @NotNull Integer stock ){
}
