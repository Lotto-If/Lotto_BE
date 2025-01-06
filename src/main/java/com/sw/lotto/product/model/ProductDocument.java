package com.sw.lotto.product.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
public abstract class ProductDocument {
    @Id
    private String pid;

    @Field(type = FieldType.Text)
    private String pname;

    @Field(type = FieldType.Keyword)
    private String pcode;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(type = FieldType.Keyword)
    private String imageURL;
}
