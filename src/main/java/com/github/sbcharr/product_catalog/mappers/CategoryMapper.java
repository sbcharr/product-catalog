package com.github.sbcharr.product_catalog.mappers;

import com.github.sbcharr.product_catalog.dtos.request.CategoryRequestDto;
import com.github.sbcharr.product_catalog.dtos.response.CategoryResponseDto;
import com.github.sbcharr.product_catalog.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponseDto toDto(Category category);
    Category toEntity(CategoryRequestDto dto);
}
