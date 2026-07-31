package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenderDTOResponse(@NotNull Long id,
                                @NotBlank String gender) {
}
