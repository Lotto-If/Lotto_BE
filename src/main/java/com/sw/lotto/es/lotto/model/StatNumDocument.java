package com.sw.lotto.es.lotto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@Mapping(mappingPath = "statnum-mapping.json")
@Setting(settingPath = "elastic-setting.json")
@Document(indexName = "lotto_statics")
public class StatNumDocument {
    @Id
    private String id;

    @Field(type = FieldType.Integer)
    private Integer cnt;

    @Field(type = FieldType.Integer)
    private Integer number;

    @Field(type = FieldType.Integer)
    private  Integer year;
}
