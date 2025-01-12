package com.sw.lotto.bucketlist.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.repository.AccountRepository;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.bucketlist.repository.BucketListRepository;
import com.sw.lotto.global.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BucketListService {

    private final BucketListRepository bucketListRepository;
    private final AccountRepository accountRepository;

    public BucketListEntity getBucketListForCurrentUser(Boolean isLotto){
        String signInId = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountEntity account = accountRepository.findBySignInId(signInId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return getOrCreateBucketList(account, isLotto);
    }


    public BucketListEntity getOrCreateBucketList(AccountEntity account, Boolean isLotto) {
        Optional<BucketListEntity> bucketList = bucketListRepository.findByAccountAndIsLotto(account, isLotto);
        return bucketList.orElseGet(() -> createBucketList(account, isLotto));
    }

    private BucketListEntity createBucketList(AccountEntity account, Boolean isLotto) {
        BucketListEntity newBucketList = new BucketListEntity();
        newBucketList.setAccount(account);
        newBucketList.setIsLotto(isLotto);
        return bucketListRepository.save(newBucketList);
    }
}
