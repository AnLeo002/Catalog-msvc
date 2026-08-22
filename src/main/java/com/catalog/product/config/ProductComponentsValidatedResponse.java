package com.catalog.product.config;

import com.catalog.category.persistence.CategoryEntity;
import com.catalog.product.persistence.BrandEntity;
import com.catalog.product.persistence.ColorEntity;
import com.catalog.product.persistence.GenderEntity;
import com.catalog.product.persistence.TypeEntity;
import jakarta.validation.constraints.NotBlank;

public record ProductComponentsValidatedResponse(
        @NotBlank ColorEntity color,
        @NotBlank TypeEntity type,
        @NotBlank GenderEntity gender,
        @NotBlank BrandEntity brand,
        @NotBlank CategoryEntity category
        ) {
}
