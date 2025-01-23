package com.sw.lotto.bucketlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sw.lotto.account.domain.AccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "bucketlist")
@Table(name = "bucketlist", indexes = {
        @jakarta.persistence.Index(name = "idx_account_isLotto", columnList = "account_oid")
})
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

