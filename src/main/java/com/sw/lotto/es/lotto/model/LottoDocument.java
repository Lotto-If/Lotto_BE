package com.sw.lotto.es.lotto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.util.Date;

@Getter
@Setter
@Mapping(mappingPath = "lotto-mapping.json")
@Setting(settingPath = "elastic-setting.json")
@Document(indexName = "dhlottery")
public class LottoDocument {
    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long actualWinnings;

//    @Field(type = FieldType.Date, format = DateFormat.basic_date)
//    private Date prizeDate;

    @Field(type = FieldType.Integer)
    private Integer round;

    @Field(type = FieldType.Integer)
    private Integer winnerNum;

    @Field(type = FieldType.Long)
    private Long winnings;

    @Field(type = FieldType.Text) // 최종 당첨 번호
    private String finalNumbers;
}
