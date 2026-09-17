package com.catalog.product.dataProvider;

import com.catalog.category.CategoryDataProvider;
import com.catalog.product.controller.dto.ProductDTO;
import com.catalog.product.controller.dto.ProductDTOResponse;
import com.catalog.product.persistence.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductDataProvider {
    public static ProductEntity createProductTest(){
        return ProductEntity.builder()
                .id(1L)
                .product("air max")
                .sku(createSkuTest())
                .price(BigDecimal.valueOf(1000000))
                .description("Tenis buenos bonitos pero caros")
                .type(TypeDataProvider.createTypeTest())
                .brand(BrandDataProvider.createBrandTest())
                .color(ColorDataProvider.createColorTest())
                .gender(GenderDataProvider.createGenderTest())
                .categories(List.of(CategoryDataProvider.createCategoryTest()))
                .productStockEntities(Set.of(ProductStockDataProvider.createProductStockTest()))
                .imageUrls(createImagesTest())
                .build();
    }
    public static ProductDTO createProductDTOTest(){
        return new ProductDTO(
                "air max","azul",BigDecimal.valueOf(1000000),"Tenis buenos bonitos pero caros",
                "nike", "casual","unisex",Set.of(ProductStockDataProvider.createProductCreateStockDTOTest()),
                List.of(1L),createImagesTest()
        );
    }
    public static ProductDTOResponse createProductDTOResponseTest(){

        return new ProductDTOResponse(
                1L,createSkuTest(),"air max","azul","casual","unisex",
                BigDecimal.valueOf(1000000),"Tenis buenos bonitos pero caros","nike",
                Set.of(ProductStockDataProvider.createStockDTOResponseTest()),List.of(CategoryDataProvider.createCategoryDTOResponseTest())
                ,createImagesTest());
    }
    public static List<String> createImagesTest(){
        return List.of("imagen.png","imagen2.png");
    }
    public static String createSkuTest(){
        return "AIR-NIK-AZU-UNI-CAS";
    }
}
