package com.sw.lotto.bucketlist.controller;

import com.sw.lotto.bucketlist.dto.BucketListResponseDto;
import com.sw.lotto.bucketlist.dto.CartItemRequestDto;
import com.sw.lotto.bucketlist.service.BucketListService;
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
            @RequestParam CartItemRequestDto cartItemRequestDto) {
        Long cartItemId = bucketListService.addItemToBucketList(isLotto, cartItemRequestDto);
        return ResponseEntity.ok(Map.of("cartItemId", cartItemId));
    }

    @GetMapping
    public ResponseEntity<BucketListResponseDto> getCurrentBucketList(
            @RequestParam(defaultValue = "true") Boolean isLotto) {
        BucketListResponseDto bucketList = bucketListService.getBucketListForCurrentUser(isLotto);
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
