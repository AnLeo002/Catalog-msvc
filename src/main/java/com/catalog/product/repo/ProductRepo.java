package com.catalog.product.repo;

import com.catalog.product.persistence.ProductEntity;
import com.catalog.product.persistence.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) = LOWER(:name)")
    Optional<ProductEntity> findByNameIgnoreCase(@Param("product") String product);
    @Query("SELECT p.productStockEntities FROM ProductEntity p WHERE p.id = :id")
    Set<ProductStockEntity> findProductStock(@Param("id") Long id);
}
