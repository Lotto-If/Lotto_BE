package com.sw.lotto.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductListRequeset {
    private String category;
    private String sort;
    private String searchWord;
    private int pageNumber;
    private int pageSize;
}
