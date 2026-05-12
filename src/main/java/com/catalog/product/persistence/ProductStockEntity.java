package com.catalog.product.persistence;

import com.catalog.product.persistence.primaryKey.ProductSizeId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_stock")
public class ProductStockEntity {
    @EmbeddedId
    private ProductSizeId id;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "size_id")
    private SizeEntity size;

    private Integer stock;
}
