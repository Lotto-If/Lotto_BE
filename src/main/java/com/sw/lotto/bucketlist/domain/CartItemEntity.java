package com.sw.lotto.bucketlist.domain;

import com.sw.lotto.global.common.model.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private Long targetId;
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "bucketlist_oid", nullable = false)
    private BucketListEntity bucketList;
}

