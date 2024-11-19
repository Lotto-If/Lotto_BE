package com.sw.lotto.es.lotto.controller;

import com.sw.lotto.common.controller.CommonController;
import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotto")
@RequiredArgsConstructor
public class LottoController extends CommonController {

    @Autowired
    private LottoService lottoService;

    @GetMapping("/list")
    public ResponseEntity<List<LottoDocument>> getLottoList(
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        List<LottoDocument> lottoList = lottoService.getLottoList(sortBy);
        return ResponseEntity.ok(lottoList);
    }


}