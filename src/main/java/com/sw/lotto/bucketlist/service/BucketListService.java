package com.sw.lotto.bucketlist.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.repository.AccountRepository;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.bucketlist.dto.BucketListDto;
import com.sw.lotto.bucketlist.repository.BucketListRepository;
import com.sw.lotto.bucketlist.repository.CartItemRepository;
import com.sw.lotto.global.common.model.TargetType;
import com.sw.lotto.global.common.service.CurrentUserService;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BucketListService {

    private final BucketListRepository bucketListRepository;
    private final CartItemRepository cartItemRepository;
    private final AccountRepository accountRepository;
    private final CurrentUserService currentUserService;


    public BucketListDto getBucketListForCurrentUser(Boolean isLotto) {
        AccountEntity currentUser = currentUserService.getCurrentUser();
        BucketListEntity bucketList = getOrCreateBucketList(currentUser, isLotto);
        return BucketListDto.fromEntity(bucketList);
    }

    private BucketListEntity getOrCreateBucketList(AccountEntity account, Boolean isLotto) {
        return bucketListRepository.findByAccountAndIsLotto(account, isLotto)
                .orElseGet(() -> {
                    BucketListEntity newBucketList = new BucketListEntity();
                    newBucketList.setAccount(account);
                    newBucketList.setIsLotto(isLotto);
                    return bucketListRepository.save(newBucketList);
                });
    }

    public Long addItemToBucketList(Boolean isLotto, TargetType targetType, String targetId, Integer amount) {
        AccountEntity currentUser = currentUserService.getCurrentUser();
        BucketListEntity bucketList = getOrCreateBucketList(currentUser, isLotto);

        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setTargetType(targetType);
        cartItem.setTargetId(targetId);
        cartItem.setAmount(amount);
        cartItem.setBucketList(bucketList);

        cartItemRepository.save(cartItem);

        return cartItem.getCartItemId();
    }

    public void updateCartItem(Long cartItemId, Integer newAmount) {
        CartItemEntity cartItem = findCartItemById(cartItemId);
        cartItem.setAmount(newAmount);
        cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long cartItemId) {
        CartItemEntity cartItem = findCartItemById(cartItemId);
        cartItemRepository.delete(cartItem);
    }

    private CartItemEntity findCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_ITEM));
    }
}
