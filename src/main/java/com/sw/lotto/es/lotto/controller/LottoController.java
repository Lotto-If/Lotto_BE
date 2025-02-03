package com.sw.lotto.es.lotto.controller;

import com.sw.lotto.es.lotto.dto.LottoResponseDto;
import com.sw.lotto.es.lotto.service.LottoService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lotto")
@RequiredArgsConstructor
@Validated
public class LottoController {

    private final LottoService lottoService;

    @GetMapping
    public ResponseEntity<Page<LottoResponseDto>> getAllLotto(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") int size,
            @RequestParam(defaultValue = "round") String sortBy) {
        Page<LottoResponseDto> lottoList = lottoService.getLottoList(page, size, sortBy);
        return ResponseEntity.ok(lottoList);
    }

    @GetMapping("/{round}")
    public ResponseEntity<LottoResponseDto> getLottoByRound(
            @PathVariable @Min(value = 1153, message = "회차 번호는 1153 이상이어야 합니다.") Integer round) {
        LottoResponseDto lottoResponseDto = lottoService.getLottoByRound(round);
        return ResponseEntity.ok(lottoResponseDto);
    }
}
