package com.catalog.service.service;

import com.catalog.service.controller.dto.ContentDTO;
import com.catalog.service.controller.dto.ContentDTOResponse;

import java.util.Set;

public interface ContentService {
    ContentDTOResponse findById(Long id);
    ContentDTOResponse findByContent(String content);
    Set<ContentDTOResponse> findAll();
    ContentDTOResponse createContent(ContentDTO contentDTO);
    ContentDTOResponse updateContent(ContentDTO contentDTO, Long id);
    void deleteContent(Long id);
}
