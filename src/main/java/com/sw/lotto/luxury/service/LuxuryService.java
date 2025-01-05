package com.sw.lotto.luxury.service;

import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.luxury.model.LuxuryDocument;
import com.sw.lotto.luxury.repository.LuxurySearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sw.lotto.global.exception.ExceptionCode.NON_EXISTENT_LUXURY_PRODUCT;

@Service
@RequiredArgsConstructor
public class LuxuryService {
    private final LuxurySearchRepository luxurySearchRepository;

    public LuxuryDocument getLuxuryDetail(String id) {
        return luxurySearchRepository.findById(id)
                .orElseThrow(() -> new AppException(NON_EXISTENT_LUXURY_PRODUCT));
    }

    public Page<LuxuryDocument> getLuxuriesWithSearchWordAndPageable(String searchWord, Pageable pageable) {
        return searchWord==null ? luxurySearchRepository.findAll(pageable) : luxurySearchRepository.findByNameOrBrandContaining(searchWord, pageable);
    }

    public List<LuxuryDocument> getLuxuriesWithSearchWord(String searchWord) {
        return searchWord==null ? luxurySearchRepository.findAll() : luxurySearchRepository.findByNameOrBrandContaining(searchWord);
    }
}
