package com.catalog.product.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SizeDTO (@NotBlank String size,
                       @NotBlank Long stock){
}
