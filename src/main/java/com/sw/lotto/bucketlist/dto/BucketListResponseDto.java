package com.sw.lotto.bucketlist.dto;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class BucketListResponseDto {
    private Long bucketListOid;
    private Boolean isLotto;
    private Long totalPrice;
    private Long lottoPrize;
    private List<CartItemResponseDto> cartItems;



    public static BucketListResponseDto fromBucketListEntity(BucketListEntity entity, List<CartItemEntity> cartItems) {
        return new BucketListResponseDto(
                entity.getBucketListOid(),
                entity.getIsLotto(),
                entity.getTotalPrice(),
                entity.getLottoPrize(),
                cartItems.stream().map(CartItemResponseDto::fromCartItemEntity).collect(Collectors.toList())
        );
    }
}
