package com.sw.lotto.luxury.repository;

import com.sw.lotto.luxury.model.LuxuryDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LuxurySearchRepository extends ElasticsearchRepository<LuxuryDocument, String> {
    @Query("{\"bool\": {\"should\": [ " +
            "{\"match\": {\"pname\": \"?0\"}}, " +
            "{\"match\": {\"brand\": \"?0\"}} ] }}")
    Page<LuxuryDocument> findByNameOrBrandContaining(String searchWord, Pageable pageable);

    @Query("{\"bool\": {\"should\": [ " +
            "{\"match\": {\"pname\": \"?0\"}}, " +
            "{\"match\": {\"brand\": \"?0\"}} ] }}")
    List<LuxuryDocument> findByNameOrBrandContaining(String searchWord);

}
