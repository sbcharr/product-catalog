package com.github.sbcharr.product_catalog.dtos.search.filters;

public class PriceFilter extends Filter {
    public PriceFilter(Double min, Double max, Operator operator) {
        setField("price");
        setMin(min);
        setMax(max);
        setOperator(operator);
    }
}
