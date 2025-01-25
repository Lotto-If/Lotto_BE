package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.dto.LottoDto;
import com.sw.lotto.es.lotto.model.LottoDocument;
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

    public Page<LottoDto> getLottoList(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return lottoRepository.findAll(pageable)
                .map(this::toLottoDTO);
    }

    public LottoDto getLottoByRound(Integer round) {
        return lottoRepository.findByRound(round)
                .map(this::toLottoDTO)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));
    }

    public LottoDto getLatestLotto() {
        return lottoRepository.findTopByOrderByRoundDesc()
                .map(this::toLottoDTO) // DTO로 매핑
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));
    }

    private LottoDto toLottoDTO(LottoDocument doc) {
        return new LottoDto(
                doc.getId(),
                doc.getActualWinnings(),
                doc.getPrizeDate(),
                doc.getRound(),
                doc.getWinnerNum(),
                doc.getWinnings(),
                doc.getFinalNumbers()
        );
    }
}
