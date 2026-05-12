package com.catalog.product.repo;

import com.catalog.product.persistence.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepo extends JpaRepository<SizeEntity,Long> {
}
