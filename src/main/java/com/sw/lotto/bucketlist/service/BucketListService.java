package com.sw.lotto.bucketlist.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.bucketlist.dto.BucketListResponseDto;
import com.sw.lotto.bucketlist.dto.CartItemRequestDto;
import com.sw.lotto.bucketlist.repository.BucketListRepository;
import com.sw.lotto.bucketlist.repository.CartItemRepository;
import com.sw.lotto.es.lotto.dto.LottoResponseDto;
import com.sw.lotto.es.lotto.service.LottoService;
import com.sw.lotto.global.common.service.CurrentUserService;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import com.sw.lotto.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BucketListService {

    private final BucketListRepository bucketListRepository;
    private final CartItemRepository cartItemRepository;
    private final LottoService lottoService;
    private final CurrentUserService currentUserService;
    private final ProductService productService;


    public BucketListResponseDto getBucketListForCurrentUser(Boolean isLotto) {
        AccountEntity currentUser = currentUserService.getCurrentUser();
        BucketListEntity bucketList = getOrCreateBucketList(currentUser, isLotto);

        List<CartItemEntity> cartItems = cartItemRepository.findByBucketList(bucketList);
        return BucketListResponseDto.fromBucketListEntity(bucketList, cartItems);
    }

    private BucketListEntity getOrCreateBucketList(AccountEntity account, Boolean isLotto) {
        return bucketListRepository.findByAccountAndIsLotto(account, isLotto)
                .orElseGet(() -> {
                    LottoResponseDto latestLottoInfo = isLotto ? lottoService.getLatestLotto() : null;
                    BucketListEntity newBucketList = BucketListEntity.create(
                            account, isLotto,
                            latestLottoInfo != null ? latestLottoInfo.getRound() : null,
                            latestLottoInfo != null ? latestLottoInfo.getActualWinnings() : null
                    );
                    return bucketListRepository.save(newBucketList);
                });
    }

    @Transactional
    public Long addItemToBucketList(Boolean isLotto, CartItemRequestDto cartItemRequestDto) {
        AccountEntity currentUser = currentUserService.getCurrentUser();
        BucketListEntity bucketList = getOrCreateBucketList(currentUser, isLotto);
        Long price = productService.getProductDetail(
                cartItemRequestDto.getTargetId(),
                cartItemRequestDto.getTargetType().toString()).getPrice();

        validateLottoLimit(bucketList, price * cartItemRequestDto.getAmount());
        updateTotalPrice(bucketList, price * cartItemRequestDto.getAmount());

        CartItemEntity cartItem = cartItemRequestDto.toCartItemEntity(bucketList);
        cartItemRepository.save(cartItem);

        return cartItem.getCartItemId();
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        CartItemEntity cartItem = findCartItemById(cartItemId);
        Long price = productService.getProductDetail(cartItem.getTargetId(), cartItem.getTargetType().toString()).getPrice();

        updateTotalPrice(cartItem.getBucketList(), -(price * cartItem.getAmount()));
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void updateCartItem(Long cartItemId, Integer newAmount) {
        CartItemEntity cartItem = findCartItemById(cartItemId);
        int quantityDifference = newAmount - cartItem.getAmount();
        Long price = productService.getProductDetail(cartItem.getTargetId(), cartItem.getTargetType().toString()).getPrice();

        updateTotalPrice(cartItem.getBucketList(), price * quantityDifference);
        cartItem.setAmount(newAmount);
        cartItemRepository.save(cartItem);
    }

    private CartItemEntity findCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_ITEM));
    }

    private void validateLottoLimit(BucketListEntity bucketList, Long priceToAdd) {
        if (bucketList.getLottoPrize() != null &&
                (bucketList.getTotalPrice() + priceToAdd > bucketList.getLottoPrize())) {
            throw new AppException(ExceptionCode.LOTTO_LIMIT_EXCEEDED);
        }
    }

    private void updateTotalPrice(BucketListEntity bucketList, Long priceDifference) {
        bucketList.setTotalPrice(bucketList.getTotalPrice() + priceDifference);
    }
}
