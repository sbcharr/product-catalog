package com.github.sbcharr.product_catalog.dtos.search.filters;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Filter {
    private String field;              // e.g., "category", "brand", "price"
    private List<String> values;         // e.g., "Apple", "Samsung" for category or brand
    private Operator operator;          // e.g., "eq", "in", "range"
    private Double min;                 // for range filters
    private Double max;

    // for range filters
    public enum Operator {
        EQ, IN, RANGE
    }
}
