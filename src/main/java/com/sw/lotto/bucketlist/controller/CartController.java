package com.sw.lotto.bucketlist.controller;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.bucketlist.service.BucketListService;
import com.sw.lotto.global.common.model.TargetType;
import com.sw.lotto.bucketlist.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final BucketListService bucketListService;

    @PostMapping("/add")
    public ResponseEntity<CartItemEntity> addItemToBucketList(
            @RequestParam Boolean isLotto,
            @RequestParam TargetType targetType,
            @RequestParam String targetId,
            @RequestParam Integer amount
    ) {
        CartItemEntity addedItem = cartItemService.addItemToBucketList(isLotto, targetType, targetId, amount);
        return ResponseEntity.ok(addedItem);
    }

    @GetMapping("/current")
    public ResponseEntity<BucketListEntity> getCurrentBucketlist(Boolean isLotto){
        BucketListEntity bucketlist = bucketListService.getBucketListForCurrentUser(isLotto);
        return ResponseEntity.ok(bucketlist);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public void removeCartItem(
            @RequestParam Boolean isLotto,  // isLotto를 요청 파라미터로 받기
            @PathVariable Long cartItemId
    ) {
        cartItemService.removeCartItem(cartItemId, isLotto); // 서비스에서 해당 장바구니와 아이템 삭제
    }

    @PutMapping("/update/{cartItemId}")
    public void updateCartItem(
            @RequestParam Boolean isLotto,  // isLotto를 요청 파라미터로 받기
            @PathVariable Long cartItemId,
            @RequestParam Integer newAmount  // 새로운 수량을 요청 파라미터로 받기
    ) {
        cartItemService.updateCartItem(cartItemId, newAmount, isLotto); // 서비스에서 해당 아이템의 수량을 업데이트
    }
}
