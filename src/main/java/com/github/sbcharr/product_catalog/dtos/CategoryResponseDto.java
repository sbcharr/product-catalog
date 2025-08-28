package com.github.sbcharr.product_catalog.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponseDto extends BaseDto {
    private String name;
    private String description;
}
