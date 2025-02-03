package com.sw.lotto.es.lotto.service;

import com.sw.lotto.es.lotto.dto.StatNumResponseDto;
import com.sw.lotto.es.lotto.model.StatNumDocument;
import com.sw.lotto.es.lotto.repository.StatNumRepository;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatNumService {

    private final StatNumRepository statNumRepository;

    public Page<StatNumResponseDto> getStatNumList(Integer year, String sortBy, int page, int size) {
        Sort sort = Sort.by(
                "number".equalsIgnoreCase(sortBy) ? Sort.Direction.ASC : Sort.Direction.DESC,
                "number".equalsIgnoreCase(sortBy) ? "number" : "cnt"
        );
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StatNumDocument> documents = statNumRepository.findByYear(year, pageable);
        if (documents.isEmpty()) {
            throw new AppException(ExceptionCode.NON_EXISTENT_LOTTO);
        }
        return documents.map(StatNumResponseDto::fromStatNumDoc);
    }
}
