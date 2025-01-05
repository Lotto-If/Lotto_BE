package com.sw.lotto.realEstate.service;

import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.realEstate.model.RealEstateDocument;
import com.sw.lotto.realEstate.repository.RealEstateSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sw.lotto.global.exception.ExceptionCode.NON_EXISTENT_REAL_ESTATE_PRODUCT;

@Service
@RequiredArgsConstructor
public class RealEstateService {
    private final RealEstateSearchRepository realEstateSearchRepository;

    public RealEstateDocument getRealEstateDetail(String id) {
        return realEstateSearchRepository.findById(id)
                .orElseThrow(() -> new AppException(NON_EXISTENT_REAL_ESTATE_PRODUCT));
    }

    public Page<RealEstateDocument> getRealEstatesWithSearchWordAndPageable(String searchWord, Pageable pageable) {
        return searchWord==null ? realEstateSearchRepository.findAll(pageable) : realEstateSearchRepository.findByPname(searchWord, pageable);
    }

    public List<RealEstateDocument> getRealEstatesWithSearchWord(String searchWord) {
        return searchWord==null ? realEstateSearchRepository.findAll() : realEstateSearchRepository.findByPname(searchWord);
    }
}
