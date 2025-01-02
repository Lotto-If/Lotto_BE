package com.sw.lotto.bucketlist.repository;

import com.sw.lotto.bucketlist.domain.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}