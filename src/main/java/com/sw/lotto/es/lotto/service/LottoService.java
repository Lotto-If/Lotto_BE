package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LottoService {

    @Autowired
    private LottoRepository lottoRepository;

    public List<LottoDocument> getLottoList(String sortBy) {
        Sort sort = Sort.unsorted();

        if ("round".equals(sortBy)) {
            sort = Sort.by(Sort.Order.desc("round"));
        } else if ("actualWinnings".equals(sortBy)) {
            sort = Sort.by(Sort.Order.desc("actualWinnings"));
        } else if ("winnings".equals(sortBy)) {
            sort = Sort.by(Sort.Order.desc("winnings"));
        }

        return lottoRepository.findAll(sort);
    }
}
