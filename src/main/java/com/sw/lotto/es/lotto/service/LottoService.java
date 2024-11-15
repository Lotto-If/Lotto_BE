package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LottoService {

    @Autowired
    private LottoRepository lottoRepository;

    // 모든 로또 리스트 조회
    public List<LottoDocument> getAllLottos() {
        return lottoRepository.findAll();
    }

    // 특정 로또 세부 정보 조회
    public Optional<LottoDocument> getLottoDetails(Integer round) {
        return lottoRepository.findByRound(round);
    }
}

