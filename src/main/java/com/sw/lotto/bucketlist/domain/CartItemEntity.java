package com.sw.lotto.bucketlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sw.lotto.global.common.model.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "cartItem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Column(nullable = false)
    private String targetId;

    @Column(nullable = false)
    private Integer amount;     // 수량

    @ManyToOne
    @JoinColumn(name = "bucket_list_oid", nullable = false)
    private BucketListEntity bucketList;
}

