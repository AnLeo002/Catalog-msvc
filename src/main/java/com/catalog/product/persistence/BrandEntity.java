package com.catalog.product.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brands")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    @OneToMany(targetEntity = ProductEntity.class, fetch = FetchType.LAZY,mappedBy = "brand")
    private List<ProductEntity> products;
}
