package com.sw.lotto.realEstate.model;

import com.sw.lotto.product.model.ProductDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.geo.Point;

@Getter
@Setter
@Document(indexName = "real_estate")
@Mapping(mappingPath = "realestate-mapping.json")
@Setting(settingPath = "elastic-setting.json")
public class RealEstateDocument extends ProductDocument {

    @Id
    private String pid;

    @Field(type = FieldType.Text)
    private String pname;

    @Field(type = FieldType.Keyword)
    private String imageURL;

    @Field(type = FieldType.Keyword)
    private String pcode;

    @Field(type = FieldType.Text)
    private String address;

    @GeoPointField
    private Point location;

}