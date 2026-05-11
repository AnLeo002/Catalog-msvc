package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SizeDTOResponse(@NotBlank Long id,
                              @NotBlank String size,
                              @NotBlank Long stock) {
}
