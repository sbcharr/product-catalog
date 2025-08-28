package com.github.sbcharr.product_catalog.dtos.search;

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
    private List<SortParams> sortParams = new ArrayList<>();
}
