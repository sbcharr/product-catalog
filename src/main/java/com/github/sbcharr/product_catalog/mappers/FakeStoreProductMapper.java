package com.github.sbcharr.product_catalog.mappers;

import com.github.sbcharr.product_catalog.dtos.requestdtos.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FakeStoreProductMapper {
    FakeStoreProductMapper INSTANCE = Mappers.getMapper(FakeStoreProductMapper.class);

    FakeStoreProductDto toDto(Product product);
    Product toEntity(FakeStoreProductDto dto);
}
