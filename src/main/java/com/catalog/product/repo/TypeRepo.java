package com.catalog.product.repo;

import com.catalog.product.persistence.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepo extends JpaRepository<TypeEntity,Long> {
    @Query("SELECT t FROM TypeEntity t WHERE LOWER(t.type) = LOWER(:type)")
    Optional<TypeEntity> findByTypeIgnoreCase(@Param("type")String type);
    boolean existsByTypeIgnoreCaseAndIdNot(String type, Long id);
}
