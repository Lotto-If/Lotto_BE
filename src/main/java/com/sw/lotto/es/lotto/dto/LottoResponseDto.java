package com.sw.lotto.es.lotto.dto;

import com.sw.lotto.es.lotto.model.LottoDocument;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LottoResponseDto {
    private String id;
    private Long actualWinnings;
    private String prizeDate;
    private Integer round;
    private Integer winnerNum;
    private Long winnings;
    private String finalNumbers;

    public static LottoResponseDto fromLottoDoc(LottoDocument doc) {
        return new LottoResponseDto(
                doc.getId(),
                doc.getActualWinnings(),
                doc.getPrizeDate(),
                doc.getRound(),
                doc.getWinnerNum(),
                doc.getWinnings(),
                doc.getFinalNumbers()
        );
    }
}