package com.sw.lotto.es.car.repository;

import com.sw.lotto.es.car.model.CarDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSearchRepository extends ElasticsearchRepository<CarDocument, Long> {
    List<CarDocument> findByName(String name);
    List<CarDocument> findAll();
}
