package com.sw.lotto.es.luxury.repository;

import com.sw.lotto.es.car.model.CarDocument;
import com.sw.lotto.es.luxury.model.LuxuryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LuxurySearchRepository extends ElasticsearchRepository<LuxuryDocument, String> {
}
