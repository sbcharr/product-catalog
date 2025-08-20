package com.github.sbcharr.product_catalog.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDto {
    private String name;
    private String description;
    private double price;
    private String imageurl;
}
