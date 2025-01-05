package com.sw.lotto.car.repository;

import com.sw.lotto.car.model.CarDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSearchRepository extends ElasticsearchRepository<CarDocument, String> {
    @Query("{\"bool\": {\"should\": [ " +
            "{\"match\": {\"pname\": \"?0\"}}, " +
            "{\"match\": {\"brand\": \"?0\"}} ] }}")
    Page<CarDocument> findByNameOrBrandContaining(String searchWord, Pageable pageable);

    @Query("{\"bool\": {\"should\": [ " +
            "{\"match\": {\"pname\": \"?0\"}}, " +
            "{\"match\": {\"brand\": \"?0\"}} ] }}")
    List<CarDocument> findByNameOrBrandContaining(String searchWord);
}
