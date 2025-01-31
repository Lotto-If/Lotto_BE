package com.sw.lotto.realEstate.model;

import com.sw.lotto.product.model.ProductDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.geo.Point;

@Getter
@Setter
@Document(indexName = "real_estate")
@Mapping(mappingPath = "realestate-mapping.json")
@Setting(settingPath = "elastic-setting.json")
@NoArgsConstructor
public class RealEstateDocument extends ProductDocument {

    @Field(type = FieldType.Text)
    private String address;

    @GeoPointField
    private Point location;

}