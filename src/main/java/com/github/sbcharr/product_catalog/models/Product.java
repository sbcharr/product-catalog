package com.github.sbcharr.product_catalog.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Product extends BaseEntity {
    private String name;
    private String description;
    private double price;
    private List<String> imageurl;
    private Category category;
}
