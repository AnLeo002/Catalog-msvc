package com.catalog.service.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ContentDTO (@NotBlank String content){
}
