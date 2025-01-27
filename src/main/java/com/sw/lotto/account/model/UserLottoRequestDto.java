package com.sw.lotto.account.model;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLottoRequestDto {
    private int round; // 회차
    private String predictedNumbers; // 예측 번호
    private Boolean notification; // 알림 여부

    public static UserLottoEntity toEntity(UserLottoRequestDto dto, AccountEntity account) {
        UserLottoEntity entity = new UserLottoEntity();
        entity.setRound(dto.getRound());
        entity.setPredictedNumbers(dto.getPredictedNumbers());
        entity.setNotification(dto.getNotification());
        entity.setAccount(account);
        return entity;
    }
}