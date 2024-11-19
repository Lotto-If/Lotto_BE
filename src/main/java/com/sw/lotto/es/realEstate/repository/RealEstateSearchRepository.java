package com.sw.lotto.es.realEstate.repository;

import com.sw.lotto.es.luxury.model.LuxuryDocument;
import com.sw.lotto.es.realEstate.model.RealEstateDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateSearchRepository extends ElasticsearchRepository<RealEstateDocument, String> {
}
