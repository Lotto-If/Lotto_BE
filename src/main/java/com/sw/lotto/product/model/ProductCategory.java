package com.sw.lotto.product.model;

import com.sw.lotto.global.common.model.BaseEnum;

import java.util.Arrays;

public enum ProductCategory implements BaseEnum {
    CAR("CAR"),
    REAL_ESTATE("REAL_ESTATE"),
    LUXURY("LUXURY"),
    ;

    private final String value;

    ProductCategory(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
