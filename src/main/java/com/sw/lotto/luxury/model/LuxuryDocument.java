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

}
