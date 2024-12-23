package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.model.StatNumDocument;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import com.sw.lotto.es.lotto.repository.StatNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LottoService {

    @Autowired
    private LottoRepository lottoRepository;
    @Autowired
    private StatNumRepository statNumRepository;

    public Page<LottoDocument> getLottoList(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return lottoRepository.findAll(pageable);
    }

    public List<StatNumDocument> getStatNumList(Integer year, String sortBy) {
        if ("number".equalsIgnoreCase(sortBy)) {
            return statNumRepository.findByYearOrderByNumberAsc(year);
        } else if ("cnt".equalsIgnoreCase(sortBy)) {
            return statNumRepository.findByYearOrderByCntDesc(year);
        } else {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
    }
}
