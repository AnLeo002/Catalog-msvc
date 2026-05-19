package com.catalog.product.repo;

import com.catalog.product.persistence.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepo extends JpaRepository<SizeEntity,Long> {
    @Query("SELECT s FROM SizeEntity s WHERE LOWER(s.size) = LOWER(:size)")
    Optional<SizeEntity> findBySizeIgnoreCase(@Param("size") String size);
}
