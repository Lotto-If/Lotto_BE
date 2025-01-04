package com.sw.lotto.bucketlist.controller;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
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

    @PostMapping("/add")
    public ResponseEntity<CartItemEntity> addItemToBucketList(
            @RequestParam AccountEntity account,
            @PathVariable Boolean isLotto,
            @RequestParam TargetType targetType,
            @RequestParam Long targetId,
            @RequestParam Integer amount
    ) {
        CartItemEntity addedItem = cartItemService.addItemToBucketList(account, isLotto, targetType, targetId, amount);
        return ResponseEntity.ok(addedItem);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartItemEntity>> getCartItems(
            @RequestParam AccountEntity account,
            @PathVariable Boolean isLotto
    ) {
        List<CartItemEntity> cartItems = cartItemService.getCartItems(account, isLotto);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public void removeCartItem(@PathVariable Long cartItemId) {
        cartItemService.removeCartItem(cartItemId);
    }
}
