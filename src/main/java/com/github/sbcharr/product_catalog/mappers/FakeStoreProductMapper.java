package com.github.sbcharr.product_catalog.mappers;

import com.github.sbcharr.product_catalog.dtos.request.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.models.Category;
import com.github.sbcharr.product_catalog.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FakeStoreProductMapper {
    FakeStoreProductMapper INSTANCE = Mappers.getMapper(FakeStoreProductMapper.class);
    @Mapping(source = "imageurl", target = "image")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "category", target = "category")
    FakeStoreProductDto toDto(Product product);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "image", target = "imageurl")
    @Mapping(source = "category", target = "category")
    Product toEntity(FakeStoreProductDto dto);

    default Category mapCategory(String categoryName) {
        if (categoryName == null) return null;
        Category category = new Category();
        category.setName(categoryName);
        return category;
    }

    default String map(Category category) {
        return category == null ? null : category.getName();
    }
}
