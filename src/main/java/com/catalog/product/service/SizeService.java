package com.catalog.product.service;

import com.catalog.product.controller.dto.SizeDTO;
import com.catalog.product.controller.dto.SizeDTOResponse;

import java.util.List;

public interface SizeService {
    SizeDTOResponse createSize (SizeDTO sizeDTO);
    SizeDTOResponse findSizeById(Long id);
    SizeDTOResponse findSizeBySize(String size);
    List<SizeDTOResponse> findAll();
    SizeDTOResponse updateSize(SizeDTO sizeDTO);
    void deleteSizeById (Long id);
}
