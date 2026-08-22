package com.catalog.category.controller.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(@NotBlank String category) {
}
