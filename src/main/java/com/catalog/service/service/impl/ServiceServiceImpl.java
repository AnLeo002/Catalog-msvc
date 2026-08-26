package com.catalog.service.service.impl;

import com.catalog.config.service.ServiceValidationEntities;
import com.catalog.service.controller.dto.ServiceDTO;
import com.catalog.service.controller.dto.ServiceDTOResponse;
import com.catalog.service.persistence.ServiceEntity;
import com.catalog.service.repo.ServiceRepo;
import com.catalog.service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepo repo;
    private final ModelMapper mapper;
    private final ServiceValidationEntities serviceValidationEntities;

    @Override
    public ServiceDTOResponse findById(Long id) {
        ServiceEntity service = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El servicio no se encuentra en base de datos"));
        return mapper.map(service,ServiceDTOResponse.class);
    }

    @Override
    public ServiceDTOResponse findByService(String service) {
        ServiceEntity serviceEntity = repo.findByServiceIgnoreCase(service)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El servicio no se encuentra en base de datos"));
        return mapper.map(serviceEntity,ServiceDTOResponse.class);
    }

    @Override
    public Set<ServiceDTOResponse> findAll() {
        List<ServiceEntity> serviceEntities = repo.findAll();
        if (serviceEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La base de datos se encuentra vacía");

        return serviceEntities.stream()
                .map(s -> mapper.map(s,ServiceDTOResponse.class))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public ServiceDTOResponse createService(ServiceDTO serviceDTO) {
        if (repo.findByServiceIgnoreCase(serviceDTO.service()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El servicio no puede ser creado por que ya existe");
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .service(serviceDTO.service())
                .description(serviceDTO.description())
                .price(serviceDTO.price())
                .categoryEntities(serviceValidationEntities.validationCategories(serviceDTO.categories()))
                .contentEntities(serviceValidationEntities.validationContents(serviceDTO.contents()))
                .build();
        ServiceEntity serviceSave = repo.save(serviceEntity);
        return mapper.map(serviceSave,ServiceDTOResponse.class);
    }

    @Override
    @Transactional
    public ServiceDTOResponse updateService(ServiceDTO serviceDTO, Long id) {
        ServiceEntity service = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El servicio no existe en base de datos"));
        if (serviceDTO.categories().isEmpty() || serviceDTO.contents().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Las categorías o los contenidos se encuentran vacíos");
        if (!service.getService().equalsIgnoreCase(serviceDTO.service())) {
            if (repo.existsByServiceIgnoreCaseAndIdNot(serviceDTO.service(), id))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un servicio con ese nombre");
            service.setService(serviceDTO.service());
        }
        if (!service.getDescription().equalsIgnoreCase(serviceDTO.description())) service.setDescription(serviceDTO.description());
        if (service.getPrice().compareTo(serviceDTO.price()) != 0) service.setPrice(serviceDTO.price());
        service.getCategoryEntities().clear();
        service.getContentEntities().clear();
        service.setContentEntities(serviceValidationEntities.validationContents(serviceDTO.contents()));
        service.setCategoryEntities(serviceValidationEntities.validationCategories(serviceDTO.categories()));

        return mapper.map(service,ServiceDTOResponse.class);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        ServiceEntity service = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El servicio no existe en base de datos"));
        try{
            service.getCategoryEntities().clear();
            service.getContentEntities().clear();
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El servicio no puede ser eliminado");
        }

    }
}
