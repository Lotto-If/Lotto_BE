package com.sw.lotto.es.lotto.repository;

import com.sw.lotto.es.lotto.model.StatNumDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface StatNumRepository extends ElasticsearchRepository<StatNumDocument, String> {
    List<StatNumDocument> findByYearOrderByCntDesc(Integer year);
    List<StatNumDocument> findByYearOrderByNumberAsc(Integer year);
}
