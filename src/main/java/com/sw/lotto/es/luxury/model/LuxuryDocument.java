package com.sw.lotto.es.luxury.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Document(indexName = "luxury")
@Mapping(mappingPath = "luxury-mapping.json")
@Setting(settingPath = "elastic-setting.json")
public class LuxuryDocument {

    @Id
    private String pid;

    @Field(type = FieldType.Text)
    private String pname;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Integer)
    private Long price;

    @Field(type = FieldType.Text)
    private String imageURL;

    @Field(type = FieldType.Text)
    private String option;

    @Field(type = FieldType.Keyword)
    private String sex;

    @Field(type = FieldType.Keyword)
    private String pcode;

}
