package com.sw.lotto.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.model.UserLottoRequestDto;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.global.common.model.BaseEntity;
import com.sw.lotto.global.common.model.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user_lotto")
@Table(name = "user_lotto", indexes = {
        @Index(name = "idx_account_oid", columnList = "account_oid"),
        @Index(name = "idx_round", columnList = "round")
})
public class UserLottoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_lotto_oid", nullable = false, unique = true)
    private Long userLottoOid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_oid", nullable = false)
    @JsonIgnore
    private AccountEntity account;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String predictedNumbers;

    @Column(nullable = false)
    private Boolean notification;

    private int correctCount;

    private String prizeRank;

    public static UserLottoEntity create(Integer round, String predictedNumbers,
                                         Boolean notification, AccountEntity account) {
        UserLottoEntity userLotto = new UserLottoEntity();
        userLotto.round = round;
        userLotto.predictedNumbers = predictedNumbers;
        userLotto.notification = notification;
        userLotto.account = account;
        return userLotto;
    }
}
