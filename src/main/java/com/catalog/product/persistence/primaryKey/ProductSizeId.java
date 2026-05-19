package com.catalog.product.persistence.primaryKey;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductSizeId implements Serializable {
    private Long productId;
    private Long sizeId;
}
