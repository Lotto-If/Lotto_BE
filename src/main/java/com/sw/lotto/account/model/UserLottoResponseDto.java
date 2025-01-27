package com.sw.lotto.account.model;

import com.sw.lotto.account.domain.UserLottoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLottoResponseDto {
    private Long userLottoOid; // 식별자
    private int round; // 회차
    private String predictedNumbers; // 예측 번호
    private Boolean notification; // 알림 여부
    private int correctCount; // 정답 개수
    private String prizeRank; // 당첨 등수

    public static UserLottoResponseDto fromEntity(UserLottoEntity entity) {
        UserLottoResponseDto dto = new UserLottoResponseDto();
        dto.setUserLottoOid(entity.getUserLottoOid());
        dto.setRound(entity.getRound());
        dto.setPredictedNumbers(entity.getPredictedNumbers());
        dto.setNotification(entity.getNotification());
        dto.setCorrectCount(entity.getCorrectCount());
        dto.setPrizeRank(entity.getPrizeRank());
        return dto;
    }
}
