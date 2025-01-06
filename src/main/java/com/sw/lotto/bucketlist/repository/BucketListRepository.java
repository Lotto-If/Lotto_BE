package com.sw.lotto.bucketlist.repository;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BucketListRepository extends JpaRepository<BucketListEntity, Boolean> {
    Optional<BucketListEntity> findByAccountAndIsLotto(AccountEntity account, Boolean isLotto);
}
