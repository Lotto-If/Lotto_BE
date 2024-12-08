package com.sw.lotto.luxury.repository;

import com.sw.lotto.luxury.model.LuxuryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuxurySearchRepository extends ElasticsearchRepository<LuxuryDocument, String> {
}
