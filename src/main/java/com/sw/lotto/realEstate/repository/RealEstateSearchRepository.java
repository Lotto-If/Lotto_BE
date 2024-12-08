package com.sw.lotto.realEstate.repository;

import com.sw.lotto.realEstate.model.RealEstateDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateSearchRepository extends ElasticsearchRepository<RealEstateDocument, String> {
}
