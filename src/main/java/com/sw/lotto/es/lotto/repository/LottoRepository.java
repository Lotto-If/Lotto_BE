package com.sw.lotto.es.lotto.repository;
import com.sw.lotto.es.lotto.model.LottoDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

public interface LottoRepository extends ElasticsearchRepository<LottoDocument, String> {
    List<LottoDocument> findAll(Sort sort);
}