package com.sw.lotto.es.lotto.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LottoDto {
    private String id;
    private Long actualWinnings;
    private String prizeDate;
    private Integer round;
    private Integer winnerNum;
    private Long winnings;
    private String finalNumbers;
}