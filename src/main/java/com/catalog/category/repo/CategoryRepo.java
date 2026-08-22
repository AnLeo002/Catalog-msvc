package com.catalog.category.repo;

import com.catalog.category.persistence.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity,Long> {
    @Query("SELECT c FROM CategoryEntity c WHERE LOWER(c.category) = LOWER(:category)")
    Optional<CategoryEntity> findByCategoryIgnoreCase(@Param("category") String category);
    boolean existsByCategoryIgnoreCaseAndIdNot(String category,Long id);
}
