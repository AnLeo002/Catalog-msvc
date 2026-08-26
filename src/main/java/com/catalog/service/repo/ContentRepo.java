package com.catalog.service.repo;

import com.catalog.service.persistence.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepo extends JpaRepository<ContentEntity,Long> {
    @Query("SELECT c FROM ContentEntity c WHERE LOWER(c.content) = LOWER(:content)")
    Optional<ContentEntity> findByContentIgnoreCase(@Param("content")String content);
    boolean existsByContentIgnoreCaseAndIdNot(String content,Long id);
}
