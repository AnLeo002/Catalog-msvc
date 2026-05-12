package com.catalog.product.repo;

import com.catalog.product.persistence.ProductStockEntity;
import com.catalog.product.persistence.primaryKey.ProductSizeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepo extends JpaRepository<ProductStockEntity, ProductSizeId> {
    //Esta query se encarga de primero suma el stock pero antes de guardar el resultado tiene que
    // encontrar la llave (productId) y que la suma cumpla con el <=, sin esto el resultado no se
    // guarda, esto detiene el envio de peticiones a la bd, haciendo que las peticiones al tiempo
    // entre administradores no generen un riesgo en la integridad de los datos
    @Modifying
    @Query("UPDATE ProductStockEntity s SET s.stock = s.stock + :amount " +
            "WHERE s.id.productId = :productId AND s.id.sizeId = :sizeId " +
            "AND (s.stock + :amount) >= 0")
    int updateStockAtomic(@Param("productId") Long productId,
                          @Param("sizeId") Long sizeId,
                          @Param("amount") Integer amount);
}
