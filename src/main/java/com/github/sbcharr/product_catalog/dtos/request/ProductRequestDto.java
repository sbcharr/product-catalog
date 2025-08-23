package com.github.sbcharr.product_catalog.dtos.request;

import com.github.sbcharr.product_catalog.dtos.BaseDto;
import com.github.sbcharr.product_catalog.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDto extends BaseDto {
    private String name;
    private String description;
    private double price;
    private String imageurl;
    private Category category;
}
