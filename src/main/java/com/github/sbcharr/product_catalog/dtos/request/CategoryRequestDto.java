package com.github.sbcharr.product_catalog.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequestDto {
    private String name;
    private String description;
}
