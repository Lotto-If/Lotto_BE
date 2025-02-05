package com.sw.lotto.account.model;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLottoRequestDto {
    private int round; // 회차
    private String predictedNumbers; // 예측 번호
    private Boolean notification; // 알림 여부

    public UserLottoEntity toUserLottoEntity(AccountEntity account){
        return UserLottoEntity.create(
                this.round,
                this.predictedNumbers,
                this.notification,
                account
        );
    }
}