package com.sw.lotto.bucketlist.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.global.common.model.TargetType;
import com.sw.lotto.bucketlist.repository.BucketListRepository;
import com.sw.lotto.bucketlist.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final BucketListRepository BucketListRepository;
    private final CartItemRepository cartItemRepository;


    public CartItemEntity addItemToBucketList(AccountEntity account, Boolean isLotto, TargetType targetType, Long targetId,  Integer amount) {
        BucketListService bucketListService = new BucketListService(BucketListRepository);
        BucketListEntity bucketList = bucketListService.getOrCreateBucketList(account, isLotto);

        // 장바구니에 상품 추가
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setTargetType(targetType);
        cartItem.setTargetId(targetId);
        cartItem.setAmount(amount);
        cartItem.setBucketList(bucketList);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItemEntity> getCartItems(AccountEntity account, Boolean isLotto) {
        // BucketList 가져오기
        BucketListService bucketListService = new BucketListService(BucketListRepository);
        BucketListEntity bucketList = bucketListService.getOrCreateBucketList(account, isLotto);

        // 해당 BucketList에 속한 CartItem 조회
        return cartItemRepository.findByBucketListBucketListOid(bucketList.getBucketListOid());
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
