package com.github.sbcharr.product_catalog.dtos.search.filters;

import java.util.List;

public class FilterFactory {
    public static Filter createFilter(String type, List<String> values, Filter.Operator operator, Double min,
                                      Double max) {
        switch (type.toLowerCase()) {
            case "brand":
                return new BrandFilter(values, operator);
            case "category":
                return new CategoryFilter(values, operator);
            case "price":
                return new PriceFilter(min, max, operator);
            default:
                throw new IllegalArgumentException("Unknown filter type: " + type);
        }
    }
}
