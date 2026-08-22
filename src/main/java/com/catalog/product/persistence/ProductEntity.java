package com.catalog.product.persistence;

import com.catalog.category.persistence.CategoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String sku;
    private String name;
    private BigDecimal price;
    private String description;
    @ManyToOne(targetEntity = BrandEntity.class, fetch = FetchType.LAZY)
    private BrandEntity brand;
    @OneToMany(targetEntity = ProductStockEntity.class, fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductStockEntity> productStockEntities;
    @ManyToOne(targetEntity = TypeEntity.class, fetch = FetchType.LAZY)
    private TypeEntity type;
    @ManyToOne(targetEntity = GenderEntity.class, fetch = FetchType.LAZY)
    private GenderEntity gender;
    @ManyToOne(targetEntity = ColorEntity.class, fetch = FetchType.LAZY)
    private ColorEntity color;
    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY)
    private CategoryEntity category;
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();
}
