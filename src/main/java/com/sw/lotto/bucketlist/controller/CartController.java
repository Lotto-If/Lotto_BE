package com.sw.lotto.bucketlist.controller;

import com.sw.lotto.bucketlist.dto.BucketListDto;
import com.sw.lotto.bucketlist.service.BucketListService;
import com.sw.lotto.global.common.model.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final BucketListService bucketListService;

    @PostMapping
    public ResponseEntity<Long> addItemToBucketList(
            @RequestParam Boolean isLotto,
            @RequestParam TargetType targetType,
            @RequestParam String targetId,
            @RequestParam Integer amount) {
        Long cartItemId = bucketListService.addItemToBucketList(isLotto, targetType, targetId, amount);
        return ResponseEntity.ok(cartItemId);
    }

    @GetMapping
    public ResponseEntity<BucketListDto> getCurrentBucketList(
            @RequestParam(defaultValue = "true") Boolean isLotto) {
        BucketListDto bucketList = bucketListService.getBucketListForCurrentUser(isLotto);
        return ResponseEntity.ok(bucketList);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        bucketListService.removeCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{cartItemId}")
    public ResponseEntity<Void> updateItem(
            @PathVariable Long cartItemId,
            @RequestParam Integer newAmount) {
        bucketListService.updateCartItem(cartItemId, newAmount);
        return ResponseEntity.ok().build();
    }


}
