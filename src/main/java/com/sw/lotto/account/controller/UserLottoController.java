package com.sw.lotto.account.controller;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.account.model.UserLottoDto;
import com.sw.lotto.account.service.UserLottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-lotto")
@RequiredArgsConstructor
public class UserLottoController {

    private final UserLottoService userLottoService;

    @PostMapping
    public ResponseEntity<UserLottoEntity> createUserLotto(@RequestBody UserLottoDto userLottoDto) {
        UserLottoEntity userLottoEntity = new UserLottoEntity();
        userLottoEntity.setRound(userLottoDto.getRound());
        userLottoEntity.setPredictedNumbers(userLottoDto.getPredictedNumbers());
        userLottoEntity.setNotification(userLottoDto.getNotification());

        UserLottoEntity savedEntity = userLottoService.saveUserLotto(userLottoEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @GetMapping
    public ResponseEntity<?> getUserLotto(@RequestParam(required = false) Integer round){
        if (round != null) {
            Optional<UserLottoEntity> userLottoEntity = userLottoService.getUserLottoByRound(round);
            return ResponseEntity.ok(userLottoEntity);
        } else {
            List<UserLottoEntity> userLottos = userLottoService.getUserLottoByAccount();
            return ResponseEntity.ok(userLottos);
        }
    }

}