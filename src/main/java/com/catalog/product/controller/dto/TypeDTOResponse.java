package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TypeDTOResponse(@NotNull Long id,
                              @NotBlank String type) {
}
