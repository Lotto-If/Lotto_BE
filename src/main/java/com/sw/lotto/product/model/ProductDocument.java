package com.sw.lotto.product.model;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
public abstract class ProductDocument {
    @Field(type = FieldType.Integer)
    private Long price;
}
