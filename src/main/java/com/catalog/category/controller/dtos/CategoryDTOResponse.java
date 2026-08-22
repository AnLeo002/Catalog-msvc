package com.catalog.category.controller.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTOResponse(@NotBlank Long id,
                                  @NotBlank String category) {
}
