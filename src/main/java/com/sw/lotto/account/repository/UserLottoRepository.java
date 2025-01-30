package com.sw.lotto.account.repository;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLottoRepository extends JpaRepository<UserLottoEntity, Integer> {
    List<UserLottoEntity> findAllByAccount(AccountEntity account);
    List<UserLottoEntity> findAllByRound(Integer round);
    Optional<UserLottoEntity> findByAccountAndRound(AccountEntity account, Integer round);
    Optional<UserLottoEntity> findByUserLottoOid(Long userLottoOid);

}