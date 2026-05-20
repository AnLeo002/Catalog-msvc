package com.catalog.product.service;

import com.catalog.product.controller.dto.BrandDTO;
import com.catalog.product.controller.dto.BrandDTOResponse;

import java.util.List;

public interface IBrandService {
    BrandDTOResponse createBrand(BrandDTO brandDTO);
    BrandDTOResponse findBrandById(Long id);
    BrandDTOResponse findBrandByName(String name);
    List<BrandDTOResponse> findAll();
    BrandDTOResponse updateBrand(BrandDTO brandDTO, Long id);
    void deleteBrandById(Long id);
}
