package com.github.sbcharr.product_catalog.dtos.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class SortParams {
    private String field;
    private Sort.Direction direction;
}
