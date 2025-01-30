package com.sw.lotto.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.global.common.model.BaseEntity;
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
    private int round; // 회차

    @Column(nullable = false, columnDefinition = "TEXT")
    private String predictedNumbers; // 예측 번호

    @Column(nullable = false)
    private Boolean notification; // 알림 여부

    private int correctCount; // 정답 개수

    private String prizeRank; // 당첨 등수 (1등, 2등, 낙첨 등)
}
