package com.sw.lotto.car.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Document(indexName = "car")
@Mapping(mappingPath = "car-mapping.json")
@Setting(settingPath = "elastic-setting.json")
public class CarDocument {

    @Id
    private String pid;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Integer)
    private Long price;

    @Field(type = FieldType.Text)
    private String thumbnail_url;

    @Field(type = FieldType.Text)
    private String trim;

    @Field(type = FieldType.Keyword)
    private String car_type;

}
