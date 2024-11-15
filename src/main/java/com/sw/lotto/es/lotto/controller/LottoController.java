package com.sw.lotto.es.lotto.controller;

import com.sw.lotto.common.controller.CommonController;
import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotto")
@RequiredArgsConstructor
public class LottoController extends CommonController {

    @Autowired
    private LottoService lottoService;

    // 모든 로또 리스트 조회 API
    @GetMapping("/list")
    public List<LottoDocument> getAllLottos() {
        return lottoService.getAllLottos();
    }

    // 선택한 라운드의 로또의 세부 정보 조회 API
    @GetMapping("/details/{round}")
    public Optional<LottoDocument> getLottoDetails(@PathVariable Integer round) {
        return lottoService.getLottoDetails(round);
    }
}
