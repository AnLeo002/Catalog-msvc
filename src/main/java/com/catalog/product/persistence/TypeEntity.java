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
@Table(name = "types")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<ProductEntity> productEntityList;
}
