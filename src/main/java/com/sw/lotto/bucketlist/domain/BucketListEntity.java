package com.sw.lotto.bucketlist.domain;

import com.sw.lotto.account.domain.AccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "bucketlist")
@Table(name = "bucketlist", indexes = {
        @Index(name = "idx_account", columnList = "account_oid")
})
public class BucketListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketListOid;

    private Boolean isLotto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_oid", nullable = false)
    private AccountEntity account;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long totalPrice = 0L;

    @Column
    private Integer lottoRound;

    @Column
    private Long lottoPrize;

    public static BucketListEntity create(AccountEntity account, Boolean isLotto, Integer lottoRound, Long lottoPrize) {
        BucketListEntity bucketList = new BucketListEntity();
        bucketList.setAccount(account);
        bucketList.setIsLotto(isLotto);

        if (Boolean.TRUE.equals(isLotto)) {
            bucketList.setLottoRound(lottoRound);
            bucketList.setLottoPrize(lottoPrize);
        }

        return bucketList;
    }
}


