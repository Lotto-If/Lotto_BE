package com.sw.lotto.car.repository;

import com.sw.lotto.car.model.CarDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSearchRepository extends ElasticsearchRepository<CarDocument, String> {
    List<CarDocument> findByName(String name);
    List<CarDocument> findAll();
}
