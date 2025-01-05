package com.sw.lotto.product.model;

import com.sw.lotto.global.common.model.BaseEnum;

public enum ProductSort implements BaseEnum {
    PRICE_LOW("PRICE_LOW"),
    PRICE_HIGH("PRICE_HIGH"),
    ;

    private final String value;

    ProductSort(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
