package com.catalog.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContentDTOResponse(@NotNull Long id,
                                 @NotBlank String content) {
}
