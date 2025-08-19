package com.github.sbcharr.product_catalog.dtos.requestdtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String image;
    private Double price;
}
