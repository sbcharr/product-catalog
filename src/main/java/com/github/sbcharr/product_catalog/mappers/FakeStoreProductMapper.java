package com.github.sbcharr.product_catalog.mappers;

import com.github.sbcharr.product_catalog.dtos.requestdtos.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FakeStoreProductMapper {
    FakeStoreProductMapper INSTANCE = Mappers.getMapper(FakeStoreProductMapper.class);
    @Mapping(source = "imageurl", target = "image")
    @Mapping(source = "name", target = "title")
    FakeStoreProductDto toDto(Product product);
    @Mapping(source = "title", target = "name")
    @Mapping(source = "image", target = "imageurl")
    Product toEntity(FakeStoreProductDto dto);
}
