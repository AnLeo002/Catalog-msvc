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
@Table(name = "genders")
public class GenderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gender;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "gender")
    private List<ProductEntity> productList;
}
