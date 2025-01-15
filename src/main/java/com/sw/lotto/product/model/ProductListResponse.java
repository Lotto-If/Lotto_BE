package com.sw.lotto.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductListResponse {
    private int totalPages;
    private long totalElements;
    private List<ProductDocument> contents;
}
