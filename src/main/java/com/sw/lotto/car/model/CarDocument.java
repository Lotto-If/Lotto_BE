package com.sw.lotto.car.model;

import com.sw.lotto.product.model.ProductDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Document(indexName = "car")
@Mapping(mappingPath = "car-mapping.json")
@Setting(settingPath = "elastic-setting.json")
public class CarDocument extends ProductDocument {

    @Id
    private String pid;

    @Field(type = FieldType.Text)
    private String pname;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Text)
    private String imageURL;

    @Field(type = FieldType.Text)
    private String trim;

    @Field(type = FieldType.Keyword)
    private String carType;

}
