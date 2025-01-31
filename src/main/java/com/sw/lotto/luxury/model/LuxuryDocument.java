package com.sw.lotto.luxury.model;

import com.sw.lotto.product.model.ProductDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Document(indexName = "luxury")
@Mapping(mappingPath = "luxury-mapping.json")
@Setting(settingPath = "elastic-setting.json")
public class LuxuryDocument extends ProductDocument {

    @Field(type = FieldType.Text)
    private String brand;

    @Field(type = FieldType.Text)
    private String option;

    @Field(type = FieldType.Keyword)
    private String sex;

    public LuxuryDocument(String pid, String pname, String pcode, Long price, String imageURL, String brand, String option, String sex) {
        super(pid, pname, pcode, price, imageURL);
        this.brand = brand;
        this.option = option;
        this.sex = sex;
    }
}
