package com.catalog.product.service.impl;

import com.catalog.product.controller.dto.ColorDTO;
import com.catalog.product.controller.dto.ColorDTOResponse;
import com.catalog.product.persistence.ColorEntity;
import com.catalog.product.repo.ColorRepo;
import com.catalog.product.service.IColorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class ColorServiceImpl implements IColorService {
    private final ColorRepo repo;

    public ColorServiceImpl(ColorRepo colorRepo) {
        this.repo = colorRepo;
    }

    @Override
    public ColorDTOResponse findById(Long id) {
        ColorEntity color = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Color no encontrado"));
        return new ColorDTOResponse(color.getId(), color.getColor());
    }

    @Override
    public ColorDTOResponse findByColor(String color) {
        ColorEntity c = repo.findByIdColorIgnoreCase(color)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Color no encontrado"));
        return new ColorDTOResponse(c.getId(), c.getColor());
    }

    @Override
    public List<ColorDTOResponse> findAll() {
        List<ColorEntity> colorEntities = repo.findAll();
        if (colorEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen colores en la base de datos");
        return colorEntities.stream()
                .map(c -> new ColorDTOResponse(c.getId(), c.getColor()))
                .toList();
    }

    @Override
    @Transactional
    public ColorDTOResponse createColor(ColorDTO colorDTO) {
        if(repo.findByIdColorIgnoreCase(colorDTO.color()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"El color ya se encuentra en la base de datos");
        ColorEntity color = ColorEntity.builder()
                .color(colorDTO.color())
                .build();
        ColorEntity colorSave = repo.save(color);
        return new ColorDTOResponse(colorSave.getId(), colorSave.getColor());
    }

    @Override
    @Transactional
    public ColorDTOResponse updateColor(ColorDTO colorDTO, Long id) {
        ColorEntity color = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El color no se encuentra en la base de datos"));
        if(repo.existsByColorIgnoreCaseAndIdNot(colorDTO.color(),id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"El color ya se encuentra en la base de datos");
        color.setColor(colorDTO.color());
        repo.save(color);
        return new ColorDTOResponse(color.getId(),color.getColor());
    }

    @Override
    @Transactional
    public void deleteColor(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El Color que se quiere eliminar no existe");
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El color no pudo ser eliminado");
        }
    }
}
