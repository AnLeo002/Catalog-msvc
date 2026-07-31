package com.catalog.product.repo;

import com.catalog.product.persistence.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepo extends JpaRepository<GenderEntity,Long> {
    @Query("SELECT g FROM GenderEntity g WHERE LOWER(g.gender) = LOWER(:gender)")
    Optional<GenderEntity> findByGenderIgnoreCase(@Param("gender")String gender);
    boolean existsByGenderIgnoreCaseAndIdNot(String gender,Long id);
}
