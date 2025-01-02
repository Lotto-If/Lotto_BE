package com.sw.lotto.account.repository;

import com.sw.lotto.account.domain.UserLottoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLottoRepository extends JpaRepository<UserLottoEntity, Long> {
}