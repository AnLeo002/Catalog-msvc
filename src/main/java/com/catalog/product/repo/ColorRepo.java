package com.catalog.product.repo;

import com.catalog.product.persistence.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepo extends JpaRepository<ColorEntity, Long> {
    @Query("SELECT c FROM ColorEntity c WHERE LOWER(c.color) = LOWER(:color)")
    Optional<ColorEntity> findByIdColorIgnoreCase(@Param("color") String color);
    boolean existsByColorIgnoreCaseAndIdNot(String color, Long id);
}
