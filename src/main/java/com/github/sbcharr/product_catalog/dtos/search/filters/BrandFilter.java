package com.github.sbcharr.product_catalog.dtos.search.filters;

import java.util.List;

public class BrandFilter extends Filter {
    public BrandFilter(List<String> values, Operator operator) {
        setField("brand");
        setValues(values);
        setOperator(operator);
    }
}
