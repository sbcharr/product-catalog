package com.github.sbcharr.product_catalog.dtos.search.filters;

import java.util.List;

public class CategoryFilter extends Filter {

    public CategoryFilter(List<String> values, Operator operator) {
        setField("category");
        setValues(values);
        setOperator(operator);
    }
}
