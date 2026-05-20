package com.catalog.product.service.impl;

import com.catalog.product.controller.dto.SizeDTO;
import com.catalog.product.controller.dto.SizeDTOResponse;
import com.catalog.product.persistence.SizeEntity;
import com.catalog.product.repo.SizeRepo;
import com.catalog.product.service.ISizeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class SizeServiceImpl implements ISizeService {
    private final SizeRepo repo;

    public SizeServiceImpl(SizeRepo repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public SizeDTOResponse createSize(SizeDTO sizeDTO) {
        if(repo.findBySizeIgnoreCase(sizeDTO.size()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"La talla se encuentra en base de datos");

        SizeEntity size = SizeEntity.builder()
                .size(sizeDTO.size())
                .build();
        repo.save(size);
        return new SizeDTOResponse(size.getId(),size.getSize());
    }

    @Override
    public SizeDTOResponse findSizeById(Long id) {
        return repo.findById(id)
                .map(size -> new SizeDTOResponse(size.getId(),size.getSize()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La talla no se encuentra en la base de datos"));
    }

    @Override
    public SizeDTOResponse findSizeBySize(String size) {
        return repo.findBySizeIgnoreCase(size)
                .map(s -> new SizeDTOResponse(s.getId(),s.getSize()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La talla no se encuentra en la base de datos"));
    }

    @Override
    public List<SizeDTOResponse> findAll() {
        List<SizeEntity> sizeEntities = repo.findAll();
        if (sizeEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen tallas en la base de datos");
        return sizeEntities.stream()
                .map(sizeEntity -> new SizeDTOResponse(sizeEntity.getId(), sizeEntity.getSize()))
                .toList();
    }

    @Override
    @Transactional
    public SizeDTOResponse updateSize(SizeDTO sizeDTO,Long id) {
        SizeEntity size = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La talla no se encuentra en la base de datos"));
        size.setSize(sizeDTO.size());
        return new SizeDTOResponse(size.getId(),size.getSize());
    }

    @Override
    @Transactional
    public void deleteSizeById(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La talla no se encuentra en la base de datos");
        try{
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"La talla no puede ser eliminada");
        }
    }
}
