package com.catalog.config.product;

import com.catalog.category.persistence.CategoryEntity;
import com.catalog.product.persistence.BrandEntity;
import com.catalog.product.persistence.ColorEntity;
import com.catalog.product.persistence.GenderEntity;
import com.catalog.product.persistence.TypeEntity;

import java.util.List;

public record ProductComponentsValidatedResponse(
        ColorEntity color,
        TypeEntity type,
        GenderEntity gender,
        BrandEntity brand,
        List<CategoryEntity> categories
        ) {
}
