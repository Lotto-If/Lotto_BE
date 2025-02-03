package com.sw.lotto.bucketlist.dto;

import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.global.common.model.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponseDto {
    private Long cartItemId;
    private TargetType targetType;
    private String targetId;
    private Integer amount;

    public static CartItemResponseDto fromCartItemEntity(CartItemEntity entity) {
        return new CartItemResponseDto(
                entity.getCartItemId(),
                entity.getTargetType(),
                entity.getTargetId(),
                entity.getAmount()
        );
    }
}