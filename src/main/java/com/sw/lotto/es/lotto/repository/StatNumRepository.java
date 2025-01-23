package com.sw.lotto.es.lotto.repository;

import com.sw.lotto.es.lotto.model.StatNumDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatNumRepository extends ElasticsearchRepository<StatNumDocument, String> {
    Page<StatNumDocument> findByYear(Integer year, Pageable pageable);
}
