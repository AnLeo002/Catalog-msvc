package com.catalog.service.controller.dto;

import com.catalog.category.controller.dtos.CategoryDTOResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ServiceDTOResponse(@NotNull Long id,
                                 @NotBlank String service,
                                 @NotBlank String description,
                                 @NotNull BigDecimal price,
                                 @NotBlank List<CategoryDTOResponse> categories,
                                 @NotBlank List<ContentDTOResponse> contents) {
}
