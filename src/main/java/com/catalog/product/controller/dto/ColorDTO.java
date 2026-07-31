package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ColorDTO(@NotBlank String color) {
}
