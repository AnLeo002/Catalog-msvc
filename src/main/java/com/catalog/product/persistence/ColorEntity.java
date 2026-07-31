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
@Table(name = "colors")
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "color")
    private List<ProductEntity> productList;
}
