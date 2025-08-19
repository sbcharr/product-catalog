package com.github.sbcharr.product_catalog.mappers;

import com.github.sbcharr.product_catalog.dtos.requestdtos.CategoryRequestDto;
import com.github.sbcharr.product_catalog.dtos.responsedtos.CategoryResponseDto;
import com.github.sbcharr.product_catalog.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponseDto toDto(Category category);
    Category toEntity(CategoryRequestDto dto);
}
