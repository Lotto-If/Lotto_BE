package com.sw.lotto.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
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

    public ProductDocument(String pid, String pname, String pcode, Long price, String imageURL) {
        this.pid = pid;
        this.pname = pname;
        this.pcode = pcode;
        this.price = price;
        this.imageURL = imageURL;
    }
}
