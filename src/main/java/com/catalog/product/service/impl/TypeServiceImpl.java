package com.catalog.product.service.impl;

import com.catalog.product.controller.dto.TypeDTO;
import com.catalog.product.controller.dto.TypeDTOResponse;
import com.catalog.product.persistence.TypeEntity;
import com.catalog.product.repo.TypeRepo;
import com.catalog.product.service.ITypeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class TypeServiceImpl implements ITypeService {
    private final TypeRepo repo;

    public TypeServiceImpl(TypeRepo repo) {
        this.repo = repo;
    }

    @Override
    public TypeDTOResponse findById(Long id) {
        TypeEntity type = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El tipo no se encuentra en la base de datos"));
        return new TypeDTOResponse(type.getId(),type.getType());
    }

    @Override
    public TypeDTOResponse findByType(String type) {
        TypeEntity t = repo.findByTypeIgnoreCase(type)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El tipo no se encuentra en la base de datos"));
        return new TypeDTOResponse(t.getId(),t.getType());
    }

    @Override
    public List<TypeDTOResponse> findAll() {
        List<TypeEntity> typeEntities = repo.findAll();
        if (typeEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT,"La base de datos se encuentra vacía");
        return typeEntities.stream()
                .map(t-> new TypeDTOResponse(t.getId(),t.getType()))
                .toList();
    }

    @Override
    @Transactional
    public TypeDTOResponse createType(TypeDTO typeDTO) {
        if (repo.findByTypeIgnoreCase(typeDTO.type()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"El tipo ya se encuentra en la base de datos");
        TypeEntity type = TypeEntity.builder()
                .type(typeDTO.type())
                .build();
        repo.save(type);
        return new TypeDTOResponse(type.getId(), type.getType());
    }

    @Override
    @Transactional
    public TypeDTOResponse updateType(TypeDTO typeDTO, Long id) {
        TypeEntity type = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El tipo no se encuentra en la base de datos"));
        if (repo.existsByTypeIgnoreCaseAndIdNot(typeDTO.type(), id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"El tipo ya se encuentra en la base de datos");
        type.setType(typeDTO.type());
        repo.save(type);
        return new TypeDTOResponse(type.getId(), type.getType());
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"EL tipo no se encuentra en la base de datos");
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El tipo no puede ser eliminado de la base de datos");
        }

    }
}
