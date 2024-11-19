package com.sw.lotto.es.lotto.controller;

import com.sw.lotto.common.controller.CommonController;
import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lotto")
@RequiredArgsConstructor
public class LottoController extends CommonController {

    @Autowired
    private LottoService lottoService;

    @GetMapping("/list")
    public Page<LottoDocument> getAllLotto(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "round") String sortBy) {
        return lottoService.getLottoList(page, size, sortBy);
    }

}