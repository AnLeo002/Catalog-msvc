package com.catalog.service.service.impl;

import com.catalog.service.controller.dto.ContentDTO;
import com.catalog.service.controller.dto.ContentDTOResponse;
import com.catalog.service.persistence.ContentEntity;
import com.catalog.service.repo.ContentRepo;
import com.catalog.service.service.ContentService;
import lombok.RequiredArgsConstructor;
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
public class ContentServiceImpl implements ContentService {
    private final ContentRepo repo;
    @Override
    public ContentDTOResponse findById(Long id) {
        ContentEntity content = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenido no se encuentra en la base de datos"));
        return new ContentDTOResponse(content.getId(), content.getContent());
    }

    @Override
    public ContentDTOResponse findByContent(String content) {
        ContentEntity contentEntity = repo.findByContentIgnoreCase(content)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenido no se encuentra en la base de datos"));
        return new ContentDTOResponse(contentEntity.getId(), contentEntity.getContent());
    }

    @Override
    public Set<ContentDTOResponse> findAll() {
        List<ContentEntity> contentEntities = repo.findAll();
        if (contentEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La base de datos se encuentra vacía");
        return contentEntities.stream()
                .map(c -> new ContentDTOResponse(c.getId(), c.getContent()))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public ContentDTOResponse createContent(ContentDTO contentDTO) {
        if (repo.findByContentIgnoreCase(contentDTO.content()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"El contenido ya se encuentra en base de datos");
        ContentEntity content = ContentEntity.builder()
                .content(contentDTO.content())
                .build();
        ContentEntity contentSave = repo.save(content);
        return new ContentDTOResponse(contentSave.getId(), contentSave.getContent());
    }

    @Override
    @Transactional
    public ContentDTOResponse updateContent(ContentDTO contentDTO, Long id) {
        ContentEntity content = repo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenido no esta en base de datos"));
        if (repo.existsByContentIgnoreCaseAndIdNot(contentDTO.content(), id))
            throw new ResponseStatusException(HttpStatus.CONFLICT,"No es posible actualizar por que el contenido ya existe");
        content.setContent(contentDTO.content());
        return new ContentDTOResponse(content.getId(), content.getContent());
    }

    @Override
    public void deleteContent(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenido a eliminar no se encuentra en base de datos");
        try{
            repo.deleteById(id);
            repo.flush();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"No es posible eliminar el contenido");
        }
    }
}
