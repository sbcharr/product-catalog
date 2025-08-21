package com.github.sbcharr.product_catalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseEntity {
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private Category category;
    // related to business
    @JsonIgnore
    private Boolean isSaleSpecific;
}
