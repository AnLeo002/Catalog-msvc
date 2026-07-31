package com.catalog.product.service;

import com.catalog.product.controller.dto.GenderDTO;
import com.catalog.product.controller.dto.GenderDTOResponse;

import java.util.List;

public interface IGenderService {
    GenderDTOResponse findById(Long id);
    GenderDTOResponse findByGender(String gender);
    List<GenderDTOResponse> findAll();
    GenderDTOResponse createGender(GenderDTO genderDTO);
    GenderDTOResponse updateGender(GenderDTO genderDTO, Long id);
    void deleteGender(Long id);
}
