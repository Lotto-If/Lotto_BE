package com.sw.lotto.bucketlist.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.global.common.model.TargetType;
import com.sw.lotto.bucketlist.repository.BucketListRepository;
import com.sw.lotto.bucketlist.repository.CartItemRepository;
import com.sw.lotto.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final BucketListRepository BucketListRepository;
    private final AccountRepository accountRepository;
    private final CartItemRepository cartItemRepository;
    private final BucketListService bucketListService;



    public CartItemEntity addItemToBucketList(Boolean isLotto, TargetType targetType, String targetId,  Integer amount) {
        BucketListEntity bucketList = bucketListService.getBucketListForCurrentUser(isLotto);
        if (bucketList == null) {
            throw new RuntimeException("BucketList not found for current user.");
        }

        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setTargetType(targetType);
        cartItem.setTargetId(targetId);
        cartItem.setAmount(amount);
        cartItem.setBucketList(bucketList);

        return cartItemRepository.save(cartItem);
    }

    public void updateCartItem(Long cartItemId, Integer newAmount, Boolean isLotto) {
        BucketListEntity bucketList = bucketListService.getBucketListForCurrentUser(isLotto);
        if (bucketList == null) {
            throw new RuntimeException("BucketList not found for current user.");
        }
        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (!cartItem.getBucketList().getBucketListOid().equals(bucketList.getBucketListOid())) {
            throw new RuntimeException("This cart item does not belong to the current bucket list.");
        }

        cartItem.setAmount(newAmount);
        cartItemRepository.save(cartItem);

    }

    public void removeCartItem(Long cartItemId, Boolean isLotto) {
        BucketListEntity bucketList = bucketListService.getBucketListForCurrentUser(isLotto);
        if (bucketList == null) {
            throw new RuntimeException("BucketList not found for current user.");
        }

        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (!cartItem.getBucketList().getBucketListOid().equals(bucketList.getBucketListOid())) {
            throw new RuntimeException("This cart item does not belong to the current bucket list.");
        }

        cartItemRepository.delete(cartItem);
    }

}
