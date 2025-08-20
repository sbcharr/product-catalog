package com.github.sbcharr.product_catalog.mappers;

import com.github.sbcharr.product_catalog.dtos.request.ProductRequestDto;
import com.github.sbcharr.product_catalog.dtos.response.ProductResponseDto;
import com.github.sbcharr.product_catalog.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toDto(Product product);
    Product toEntity(ProductRequestDto dto);
}
