package com.sw.lotto.es.car.service;

import com.sw.lotto.es.car.model.CarDocument;
import com.sw.lotto.es.car.repository.CarSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

//@EnableElasticsearchRepositories(
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CarSearchRepository.class),
//        }
//)
@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarSearchRepository carSearchRepository;

    public void test() {
        log.info("test start");
        List<CarDocument> cd = carSearchRepository.findAll();
        log.info("cd-1: {}", cd.get(0));
        log.info("cd-2: {}", cd.get(1));
        log.info("cd-size: {}", cd.size());
    }
}
