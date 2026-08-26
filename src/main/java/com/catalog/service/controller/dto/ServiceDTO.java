package com.catalog.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ServiceDTO(@NotBlank String service,
                         @NotBlank String description,
                         @NotNull BigDecimal price,
                         @NotEmpty List<Long> categories,
                         @NotEmpty List<Long> contents) {
}
