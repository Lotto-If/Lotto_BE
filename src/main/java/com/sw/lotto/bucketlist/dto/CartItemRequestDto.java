package com.sw.lotto.bucketlist.dto;

import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.global.common.model.TargetType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDto {
    private TargetType targetType;
    private String targetId;
    private Integer amount;

    public CartItemEntity toCartItemEntity(BucketListEntity bucketList){
        return CartItemEntity.create(
                this.targetId,
                this.targetType,
                this.amount,
                bucketList
        );
    }
}