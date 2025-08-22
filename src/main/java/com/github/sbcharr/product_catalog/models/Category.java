package com.github.sbcharr.product_catalog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
