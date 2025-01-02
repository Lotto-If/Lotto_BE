package com.sw.lotto.bucketlist.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.bucketlist.domain.TargetType;
import com.sw.lotto.bucketlist.repository.BucketListRepository;
import com.sw.lotto.bucketlist.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final BucketListRepository bucketListRepository;
    private final CartItemRepository cartItemRepository;

    public BucketListEntity getOrCreateBucketList(AccountEntity account, Boolean isLotto) {
        // 유저당 true/false 장바구니는 하나만 가능
        return bucketListRepository.findByAccountAccountOidAndIsLotto(account, isLotto)
                .orElseGet(() -> {
                    BucketListEntity newBucketList = new BucketListEntity();
                    newBucketList.setAccount(account); // 유저 정보 설정
                    newBucketList.setIsLotto(isLotto);
                    return bucketListRepository.save(newBucketList);
                });
    }

    public CartItemEntity addItemToBucketList(AccountEntity account, Boolean isLotto, TargetType targetType, Long targetId, Long amount) {
        BucketListEntity bucketList = getOrCreateBucketList(account, isLotto);

        // 장바구니에 상품 추가
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setTargetType(targetType);
        cartItem.setTargetId(targetId);
        cartItem.setAmount(amount);
        cartItem.setBucketList(bucketList);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItemEntity> getItemsInBucketList(AccountEntity account, Boolean isLotto) {
        BucketListEntity bucketList = getOrCreateBucketList(account, isLotto);
        return bucketList.getCartItems();
    }
}
