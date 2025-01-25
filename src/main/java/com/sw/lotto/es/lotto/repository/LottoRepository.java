package com.sw.lotto.es.lotto.repository;
import com.sw.lotto.es.lotto.model.LottoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LottoRepository extends ElasticsearchRepository<LottoDocument, String> {
    Optional<LottoDocument> findTopByOrderByRoundDesc();
    Optional<LottoDocument> findByRound(Integer round);
}