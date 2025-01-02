package com.sw.lotto.account.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.account.repository.UserLottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLottoService {

    private final UserLottoRepository userLottoRepository;

    public UserLottoEntity saveUserLotto(UserLottoEntity userLottoEntity, AccountEntity account) {
        userLottoEntity.setAccount(account); // 로그인된 사용자 설정
        return userLottoRepository.save(userLottoEntity);
    }

    public List<UserLottoEntity> getAllUserLottos(AccountEntity account) {
        return userLottoRepository.findAll(); // 필요 시 계정별 필터 추가
    }
}
