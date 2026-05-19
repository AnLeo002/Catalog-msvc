package com.catalog.product.service.impl;

import com.catalog.product.controller.dto.BrandDTO;
import com.catalog.product.controller.dto.BrandDTOResponse;
import com.catalog.product.persistence.BrandEntity;
import com.catalog.product.repo.BrandRepo;
import com.catalog.product.service.BrandService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService {
    private final BrandRepo repo;

    public BrandServiceImpl(BrandRepo brandRepo) {
        this.repo = brandRepo;
    }

    @Override
    @Transactional
    public BrandDTOResponse createBrand(BrandDTO brandDTO) {
        if (repo.findByBrandIgnoreCase(brandDTO.brand()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"La marca ya se encuentra en la base de datos");
        }
        BrandEntity brand = BrandEntity.builder()
                .brand(brandDTO.brand())
                .build();
        repo.save(brand);
        return new BrandDTOResponse(brand.getId(), brand.getBrand());
    }

    @Override
    public BrandDTOResponse findBrandById(Long id) {
        return repo.findById(id)
                .map(brand -> new BrandDTOResponse(brand.getId(),brand.getBrand()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La marca no se encuentra en la base de datos"));
    }

    @Override
    public BrandDTOResponse findBrandByName(String name) {
        return repo.findByBrandIgnoreCase(name)
                .map(brand -> new BrandDTOResponse(brand.getId(),brand.getBrand()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"La marca no se encuentra en la base de datos"));
    }

    @Override
    public List<BrandDTOResponse> findAll() {
        List<BrandEntity> brandEntities = repo.findAll();
        if (brandEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT,"No existen marcas registradas");
        return brandEntities.stream()
                .map(brand -> new BrandDTOResponse(brand.getId(), brand.getBrand()))
                .toList();
    }

    @Override
    @Transactional
    public BrandDTOResponse updateBrand(BrandDTO brandDTO, Long id) {
        BrandEntity brand = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "La marca que va a actualizar no se encuentra en la base de datos"));
        brand.setBrand(brandDTO.brand());
        repo.save(brand);
        return new BrandDTOResponse(brand.getId(), brand.getBrand());
    }

    @Override
    public void deleteBrandById(Long id) {
        if (!repo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"La marca no se encuentra en la base de datos");
        }
        try{
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"La marca no puede ser eliminada");
        }

    }
}
