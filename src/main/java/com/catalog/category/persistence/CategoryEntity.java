package com.catalog.category.persistence;

import com.catalog.product.persistence.ProductEntity;
import com.catalog.service.persistence.ServiceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    @OneToMany(targetEntity = ProductEntity.class,fetch = FetchType.LAZY,mappedBy = "category")
    private List<ProductEntity> productEntities;
    @OneToMany(targetEntity = ServiceEntity.class,fetch = FetchType.LAZY,mappedBy = "category")
    private List<ServiceEntity> serviceEntities;
}