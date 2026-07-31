package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record GenderDTO(@NotBlank String gender) {
}
