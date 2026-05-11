package com.catalog.product.persistence;

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
    private String name;

    @ManyToMany(targetEntity = ProductEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "category_product", joinColumns = @JoinColumn(name = "category"), inverseJoinColumns = @JoinColumn(name = "product"))
    private List<ProductEntity> productEntities;
}

