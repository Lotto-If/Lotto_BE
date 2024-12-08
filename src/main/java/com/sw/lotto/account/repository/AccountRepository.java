package com.sw.lotto.account.repository;

import com.sw.lotto.account.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findBySignInId(String signInId);
    Optional<AccountEntity> findByObjectId(Long accountId);
    Boolean existsBySignInId(String signInId);
}
