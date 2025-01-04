package com.sw.lotto.bucketlist.domain;

import com.sw.lotto.account.domain.AccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class BucketListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketListOid;

    private Boolean isLotto; // true: 로또 회차 지정, false: 자유 금액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_oid", nullable = false)
    private AccountEntity account;

    @OneToMany(mappedBy = "bucketList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems;

}
