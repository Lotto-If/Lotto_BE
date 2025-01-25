package com.sw.lotto.bucketlist.controller;

import com.sw.lotto.bucketlist.dto.BucketListDto;
import com.sw.lotto.bucketlist.service.BucketListService;
import com.sw.lotto.global.common.model.TargetType;
import com.sw.lotto.product.model.ProductCategory;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final BucketListService bucketListService;

    @PostMapping
    public ResponseEntity<?> addItemToBucketList(
            @RequestParam Boolean isLotto,
            @RequestParam TargetType targetType,
            @RequestParam String targetId,
            @RequestParam @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") Integer amount) {
        Long cartItemId = bucketListService.addItemToBucketList(isLotto, targetType, targetId, amount);
        return ResponseEntity.ok(Map.of("cartItemId", cartItemId));
    }

    @GetMapping
    public ResponseEntity<BucketListDto> getCurrentBucketList(
            @RequestParam(defaultValue = "true") Boolean isLotto) {
        BucketListDto bucketList = bucketListService.getBucketListForCurrentUser(isLotto);
        return ResponseEntity.ok(bucketList);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeCartItem(
            @PathVariable Long cartItemId) {
        bucketListService.removeCartItem(cartItemId);
        return ResponseEntity.ok(Map.of("message", "Item successfully removed."));
    }


    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateItem(
            @PathVariable Long cartItemId,
            @RequestParam @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") Integer newAmount ) {
        bucketListService.updateCartItem(cartItemId, newAmount);
        return ResponseEntity.ok(Map.of("message", "Item successfully updated."));
    }
}
