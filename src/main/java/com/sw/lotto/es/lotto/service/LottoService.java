package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LottoService {

    @Autowired
    private LottoRepository lottoRepository;

    public Page<LottoDocument> getLottoList(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return lottoRepository.findAll(pageable);
    }
}
