package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.dto.LottoResponseDto;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LottoService {

    private final LottoRepository lottoRepository;

    public Page<LottoResponseDto> getLottoList(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return lottoRepository.findAll(pageable)
                .map(LottoResponseDto::fromLottoDoc);
    }

    public LottoResponseDto getLottoByRound(Integer round) {
        return lottoRepository.findByRound(round)
                .map(LottoResponseDto::fromLottoDoc)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));
    }

    public LottoResponseDto getLatestLotto() {
        return lottoRepository.findTopByOrderByRoundDesc()
                .map(LottoResponseDto::fromLottoDoc) // DTO로 매핑
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));
    }

}
