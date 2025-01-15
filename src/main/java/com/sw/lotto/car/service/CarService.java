package com.sw.lotto.car.service;

import com.sw.lotto.car.model.CarDocument;
import com.sw.lotto.car.repository.CarSearchRepository;
import com.sw.lotto.global.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.sw.lotto.global.exception.ExceptionCode.NON_EXISTENT_CAR_PRODUCT;

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

    public CarDocument getCarDetail(String id) {
        return carSearchRepository.findById(id)
                .orElseThrow(() -> new AppException(NON_EXISTENT_CAR_PRODUCT));
    }

    public Page<CarDocument> getCarsWithSearchWordAndPageable(String searchWord, Pageable pageable) {
        return searchWord==null ? carSearchRepository.findAll(pageable) : carSearchRepository.findByNameOrBrandContaining(searchWord, pageable);
    }

    public List<CarDocument> getCarsWithSearchWord(String searchWord) {
        return searchWord==null ?
                StreamSupport.stream(carSearchRepository.findAll().spliterator(), false).collect(Collectors.toList())
                : carSearchRepository.findByNameOrBrandContaining(searchWord);
    }
}
