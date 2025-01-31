package com.sw.lotto.car.model;

import com.sw.lotto.product.model.ProductDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Document(indexName = "car")
@Mapping(mappingPath = "car-mapping.json")
@Setting(settingPath = "elastic-setting.json")
@NoArgsConstructor
public class CarDocument extends ProductDocument {

    @Field(type = FieldType.Text)
    private String brand;

    @Field(type = FieldType.Text)
    private String option;

}
