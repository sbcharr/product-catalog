package com.github.sbcharr.product_catalog.dtos.requestdtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductRequestDto {
    private String name;
    private String description;
    private double price;
    private List<String> imageurl;
    private CategoryRequestDto category;
}
