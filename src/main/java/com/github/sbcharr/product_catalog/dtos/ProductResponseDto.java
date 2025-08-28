package com.github.sbcharr.product_catalog.dtos;

import com.github.sbcharr.product_catalog.models.Category;
import com.github.sbcharr.product_catalog.models.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseDto extends BaseDto {
    private String name;
    private String description;
    private double price;
    private String imageurl;
    private Category category;

    public static ProductResponseDto fromEntity(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        // Set fields from product to dto
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageurl(product.getImageUrl());

        Category category = new Category();
        category.setId(product.getCategory().getId());
        category.setName(product.getCategory().getName());
        category.setDescription(product.getCategory().getDescription());
        dto.setCategory(category);

        return dto;
    }
}
