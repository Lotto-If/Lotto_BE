package com.sw.lotto.bucketlist.controller;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.CartItemEntity;
import com.sw.lotto.bucketlist.domain.TargetType;
import com.sw.lotto.bucketlist.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{isLotto}")
    public ResponseEntity<CartItemEntity> addItemToCart(
            @RequestParam AccountEntity account,
            @PathVariable Boolean isLotto,
            @RequestParam TargetType targetType,
            @RequestParam Long targetId,
            @RequestParam Long amount
    ) {
        CartItemEntity addedItem = cartService.addItemToBucketList(account, isLotto, targetType, targetId, amount);
        return ResponseEntity.ok(addedItem);
    }

    @GetMapping("/{isLotto}")
    public ResponseEntity<List<CartItemEntity>> getCartItems(
            @RequestParam AccountEntity account,
            @PathVariable Boolean isLotto
    ) {
        List<CartItemEntity> cartItems = cartService.getItemsInBucketList(account, isLotto);
        return ResponseEntity.ok(cartItems);
    }
}
