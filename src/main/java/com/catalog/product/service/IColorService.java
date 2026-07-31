package com.catalog.product.service;

import com.catalog.product.controller.dto.ColorDTO;
import com.catalog.product.controller.dto.ColorDTOResponse;

import java.util.List;

public interface IColorService {
    ColorDTOResponse findById(Long id);
    ColorDTOResponse findByColor(String color);
    List<ColorDTOResponse> findAll();
    ColorDTOResponse createColor(ColorDTO colorDTO);
    ColorDTOResponse updateColor(ColorDTO colorDTO, Long id);
    void deleteColor(Long id);

}
