package com.sw.lotto.es.lotto.repository;
import com.sw.lotto.es.lotto.model.LottoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;


public interface LottoRepository extends ElasticsearchRepository<LottoDocument, String> {
    Optional<LottoDocument> findTopByOrderByRoundDesc();
    Optional<LottoDocument> findByRound(Integer round);
}