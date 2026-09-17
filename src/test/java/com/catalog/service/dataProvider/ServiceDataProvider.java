package com.catalog.service.dataProvider;

import com.catalog.category.CategoryDataProvider;
import com.catalog.service.controller.dto.ServiceDTO;
import com.catalog.service.controller.dto.ServiceDTOResponse;
import com.catalog.service.persistence.ServiceEntity;

import java.math.BigDecimal;
import java.util.List;

public class ServiceDataProvider {

    public static ServiceEntity createServiceTest(){
        return ServiceEntity.builder()
                .id(1L)
                .service("Matrimonio pro")
                .description("Chimbita de servicio")
                .price(BigDecimal.valueOf(1200000))
                .categoryEntities(List.of(CategoryDataProvider.createCategoryTest()))
                .contentEntities(List.of(ContentDataProvider.createContentTest()))
                .build();
    }
    public static ServiceDTO createServiceDTOTest(){
        return new ServiceDTO("Matrimonio pro",
                "Chimbita de servicio",
                BigDecimal.valueOf(1200000),
                List.of(1L),
                List.of(1L));
    }
    public static ServiceDTOResponse createServiceDTOResponseTest(){

        return new ServiceDTOResponse(1L,
                "Matrimonio pro",
                "Chimbita de servicio",
                BigDecimal.valueOf(1200000),
                List.of(CategoryDataProvider.createCategoryDTOResponseTest()),
                List.of(ContentDataProvider.createContentDTOResponseTest()));
    }

}

