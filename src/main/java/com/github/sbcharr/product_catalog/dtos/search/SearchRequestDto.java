package com.github.sbcharr.product_catalog.dtos.search;

import com.github.sbcharr.product_catalog.dtos.search.filters.Filter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SearchRequestDto {
    private String query;
    private Integer page;
    private Integer pageSize;
    private List<Filter> filters = new ArrayList<>();
    private List<SortParams> sortParams = new ArrayList<>();
}
