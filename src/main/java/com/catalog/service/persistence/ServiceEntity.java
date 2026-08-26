package com.catalog.service.persistence;

import com.catalog.category.persistence.CategoryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "services")
public class  ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String service;
    private String description;
    private BigDecimal price;
    @ManyToMany(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "service_category", joinColumns = @JoinColumn(name = "service_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categoryEntities;
    @ManyToMany(targetEntity = ContentEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "service_content", joinColumns = @JoinColumn(name = "service_id"),inverseJoinColumns = @JoinColumn(name = "content_id"))
    private List<ContentEntity> contentEntities;

}