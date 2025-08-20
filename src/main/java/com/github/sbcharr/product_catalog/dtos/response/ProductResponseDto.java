package com.github.sbcharr.product_catalog.dtos.response;

import com.github.sbcharr.product_catalog.dtos.BaseDto;
import com.github.sbcharr.product_catalog.models.Category;
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
}
