package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record TypeDTO(@NotBlank String type) {
}
