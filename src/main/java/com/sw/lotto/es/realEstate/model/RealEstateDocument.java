package com.sw.lotto.es.realEstate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Document(indexName = "real_estate")
@Mapping(mappingPath = "realestate-mapping.json")
@Setting(settingPath = "elastic-setting.json")
public class RealEstateDocument {

    @Id
    private String pid;

    @Field(type = FieldType.Text)
    private String pname;

    @Field(type = FieldType.Keyword)
    private String imageURL;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(type = FieldType.Keyword)
    private String pcode;

    @Field(type = FieldType.Text)
    private String address;

    @GeoPointField
    private String location;

}