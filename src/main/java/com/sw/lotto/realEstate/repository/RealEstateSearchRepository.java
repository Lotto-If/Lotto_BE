package com.sw.lotto.realEstate.repository;

import com.sw.lotto.realEstate.model.RealEstateDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateSearchRepository extends ElasticsearchRepository<RealEstateDocument, String> {
    Page<RealEstateDocument> findByPname(String searchWord, Pageable pageable);
    List<RealEstateDocument> findByPname(String searchWord);
}
