package com.sw.lotto.bucketlist.dto;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BucketListDto {
    private Long bucketListOid;
    private Boolean isLotto;
    private List<CartItemDto> cartItems;

    public static BucketListDto fromEntity(BucketListEntity entity) {
        return new BucketListDto(
                entity.getBucketListOid(),
                entity.getIsLotto(),
                entity.getCartItems().stream().map(CartItemDto::fromEntity).collect(Collectors.toList())
        );
    }
}
