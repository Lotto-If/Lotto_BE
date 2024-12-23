package com.sw.lotto.es.lotto.repository;
import com.sw.lotto.es.lotto.model.LottoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface LottoRepository extends ElasticsearchRepository<LottoDocument, String> {
}