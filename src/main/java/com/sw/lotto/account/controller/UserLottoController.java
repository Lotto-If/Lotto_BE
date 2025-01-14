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
//        Integer latestRound = userLottoService.getLatestLottoRound();

        UserLottoEntity userLottoEntity = new UserLottoEntity();
//        userLottoEntity.setRound(latestRound);
        userLottoEntity.setRound(userLottoDto.getRound());
        userLottoEntity.setPredictedNumbers(userLottoDto.getPredictedNumbers());
        userLottoEntity.setNotification(userLottoDto.getNotification());

        UserLottoEntity savedEntity = userLottoService.saveUserLotto(userLottoEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @GetMapping
    public ResponseEntity<?> getUserLotto(@RequestParam(required = false) Integer round){
        if (round != null) {
            Optional<UserLottoEntity> userLotto = userLottoService.getUserLottoByRound(round);
            return ResponseEntity.ok(userLotto);
        } else {
            List<UserLottoEntity> userLottos = userLottoService.getUserLottoByAccount();
            return ResponseEntity.ok(userLottos);
        }
    }

    @PostMapping("/check-winnings")
    public ResponseEntity<?> checkWinnings() {
        userLottoService.checkWinningsAndNotify();
        return ResponseEntity.ok("Winnings checked and notifications sent.");
    }

}