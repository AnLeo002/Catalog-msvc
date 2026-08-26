package com.catalog.service.repo;

import com.catalog.service.persistence.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepo extends JpaRepository<ServiceEntity, Long> {
    @Query("SELECT s FROM ServiceEntity s WHERE LOWER(s.service) = LOWER(:service)")
    Optional<ServiceEntity> findByServiceIgnoreCase(@Param("service") String service);
    boolean existsByServiceIgnoreCaseAndIdNot(String service,Long id);
}
