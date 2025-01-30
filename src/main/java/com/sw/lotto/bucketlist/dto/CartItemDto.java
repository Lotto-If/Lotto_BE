package com.sw.lotto.bucketlist.dto;

import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.global.common.model.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemDto {
    private Long cartItemId;
    private TargetType targetType;
    private String targetId;
    private Integer amount;

    public static CartItemDto fromEntity(CartItemEntity entity) {
        return new CartItemDto(
                entity.getCartItemId(),
                entity.getTargetType(),
                entity.getTargetId(),
                entity.getAmount()
        );
    }
}