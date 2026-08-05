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
    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) = LOWER(:product)")
    Optional<ProductEntity> findByNameIgnoreCase(@Param("product") String product);
    @Query("SELECT p.productStockEntities FROM ProductEntity p WHERE p.id = :id")
    Set<ProductStockEntity> findProductStock(@Param("id") Long id);
    @Query(""" 
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM ProductEntity p
    WHERE LOWER(p.name) = LOWER(:name)
      AND p.brand.id = :brandId
      AND p.type.id = :typeId
      AND p.gender.id = :genderId
      AND p.color.id = :colorId
      AND (:id IS NULL OR p.id != :id)
    """)
    boolean existsByUniqueAttributes(
            @Param("name") String name,
            @Param("brandId") Long brandId,
            @Param("typeId") Long typeId,
            @Param("genderId") Long genderId,
            @Param("colorId") Long colorId,
            @Param("id") Long id
    );
}
