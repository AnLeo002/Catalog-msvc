package com.catalog.product.service;

import com.catalog.product.controller.dto.TypeDTO;
import com.catalog.product.controller.dto.TypeDTOResponse;

import java.util.List;

public interface ITypeService {
    TypeDTOResponse findById(Long id);
    TypeDTOResponse findByType(String type);
    List<TypeDTOResponse> findAll();
    TypeDTOResponse createType(TypeDTO typeDTO);
    TypeDTOResponse updateType(TypeDTO typeDTO, Long id);
    void deleteType(Long id);
}
