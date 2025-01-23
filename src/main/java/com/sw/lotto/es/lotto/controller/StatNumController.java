package com.sw.lotto.es.lotto.controller;

import com.sw.lotto.es.lotto.dto.StatNumDto;
import com.sw.lotto.es.lotto.service.LottoService;
import com.sw.lotto.es.lotto.service.StatNumService;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lotto")
@RequiredArgsConstructor
@Validated
public class StatNumController {

    private final StatNumService statNumService;


    @GetMapping("/statNum")
    public ResponseEntity<Page<StatNumDto>> getStatNum(
            @RequestParam(defaultValue = "2025") @Min(value = 2024, message = "년도는 2024 이상이어야 합니다.") Integer year,
            @RequestParam(defaultValue = "cnt") String sortBy,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") int size) {
        Page<StatNumDto> statNumList = statNumService.getStatNumList(year, sortBy, page, size);
        return ResponseEntity.ok(statNumList);
    }
}
