package com.catalog.product.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private BigDecimal price;
    private String description;
    @ManyToOne(targetEntity = BrandEntity.class, fetch = FetchType.LAZY)
    private BrandEntity brand;
    @OneToMany(targetEntity = ProductStockEntity.class, fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductStockEntity> productStockEntities;
}
