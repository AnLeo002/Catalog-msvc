package com.catalog.service.persistence;

import com.catalog.category.persistence.CategoryEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    private String description;
    private String price;
    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY)
    private CategoryEntity category;
}