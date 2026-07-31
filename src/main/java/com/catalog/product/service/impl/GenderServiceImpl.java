package com.catalog.product.service.impl;

import com.catalog.product.controller.dto.GenderDTO;
import com.catalog.product.controller.dto.GenderDTOResponse;
import com.catalog.product.persistence.GenderEntity;
import com.catalog.product.repo.GenderRepo;
import com.catalog.product.service.IGenderService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class GenderServiceImpl implements IGenderService {
    private final GenderRepo repo;

    public GenderServiceImpl(GenderRepo repo) {
        this.repo = repo;
    }

    @Override
    public GenderDTOResponse findById(Long id) {
        GenderEntity gender = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El genero no se encuentra en la base de datos"));
        return new GenderDTOResponse(gender.getId(), gender.getGender());
    }

    @Override
    public GenderDTOResponse findByGender(String gender) {
        GenderEntity g = repo.findByGenderIgnoreCase(gender)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El genero no se encuentra en la base de datos"));
        return new GenderDTOResponse(g.getId(), g.getGender());
    }

    @Override
    public List<GenderDTOResponse> findAll() {
        List<GenderEntity> genderEntities = repo.findAll();
        if (genderEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen géneros registrados");
        return genderEntities.stream()
                .map(g -> new GenderDTOResponse(g.getId(),g.getGender()))
                .toList();
    }

    @Override
    @Transactional
    public GenderDTOResponse createGender(GenderDTO genderDTO) {
        if (repo.findByGenderIgnoreCase(genderDTO.gender()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"El genero ya existe en la base de datos");
        GenderEntity gender = GenderEntity.builder()
                .gender(genderDTO.gender())
                .build();
        GenderEntity genderSave = repo.save(gender);
        return new GenderDTOResponse(genderSave.getId(), genderSave.getGender());
    }

    @Override
    @Transactional
    public GenderDTOResponse updateGender(GenderDTO genderDTO, Long id) {
        GenderEntity gender = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El genero no se encuentra en la base de datos"));
        if (repo.existsByGenderIgnoreCaseAndIdNot(genderDTO.gender(),id))throw new ResponseStatusException(HttpStatus.CONFLICT,"El genero ya se encuentra en la base de datos");
        gender.setGender(genderDTO.gender());
        repo.save(gender);
        return new GenderDTOResponse(gender.getId(), gender.getGender());
    }

    @Override
    @Transactional
    public void deleteGender(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El genero no se encuentra en la base de datos");
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El genero no puede ser eliminado de la base de datos");
        }
    }
}
