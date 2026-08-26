package com.catalog.service.service;

import com.catalog.service.controller.dto.ServiceDTO;
import com.catalog.service.controller.dto.ServiceDTOResponse;

import java.util.Set;

public interface ServiceService {
    ServiceDTOResponse findById(Long id);
    ServiceDTOResponse findByService(String service);
    Set<ServiceDTOResponse> findAll();
    ServiceDTOResponse createService(ServiceDTO serviceDTO);
    ServiceDTOResponse updateService (ServiceDTO serviceDTO, Long id);
    void deleteService(Long id);
}
