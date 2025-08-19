package com.github.sbcharr.product_catalog.dtos.responsedtos;

import com.github.sbcharr.product_catalog.dtos.BaseDto;
import com.github.sbcharr.product_catalog.dtos.requestdtos.CategoryRequestDto;
import com.github.sbcharr.product_catalog.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductResponseDto extends BaseDto {
    private String name;
    private String description;
    private double price;
    private String imageurl;
    private Category category;
}
