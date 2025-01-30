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
public class BucketListDto {
    private Long bucketListOid;
    private Boolean isLotto;
    private Long totalPrice;
    private Long lottoPrize;
    private List<CartItemDto> cartItems;


    public static BucketListDto fromEntity(BucketListEntity entity, List<CartItemEntity> cartItems) {
        return new BucketListDto(
                entity.getBucketListOid(),
                entity.getIsLotto(),
                entity.getTotalPrice(),
                entity.getLottoPrize(),
                cartItems.stream().map(CartItemDto::fromEntity).collect(Collectors.toList())
        );
    }
}
