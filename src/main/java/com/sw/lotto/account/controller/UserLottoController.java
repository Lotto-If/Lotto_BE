package com.sw.lotto.account.controller;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.account.service.UserLottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-lotto")
@RequiredArgsConstructor
public class UserLottoController {

    private final UserLottoService userLottoService;

    @PostMapping
    public ResponseEntity<UserLottoEntity> createUserLotto(@RequestBody UserLottoRequest userLottoRequest, @AuthenticationPrincipal AccountEntity account) {
        UserLottoEntity userLottoEntity = new UserLottoEntity();
        userLottoEntity.setRound(userLottoRequest.getRound());
        userLottoEntity.setPredictedNumbers(userLottoRequest.getPredictedNumbers());
        userLottoEntity.setNotification(userLottoRequest.getNotification());

        UserLottoEntity savedEntity = userLottoService.saveUserLotto(userLottoEntity, account);
        return ResponseEntity.ok(savedEntity);
    }

    @GetMapping
    public ResponseEntity<List<UserLottoEntity>> getAllUserLottos(@AuthenticationPrincipal AccountEntity account) {
        List<UserLottoEntity> userLottos = userLottoService.getAllUserLottos(account);
        return ResponseEntity.ok(userLottos);
    }

    // DTO for simplified request
    public static class UserLottoRequest {
        private int round;
        private String predictedNumbers;
        private Boolean notification;

        // Getters and Setters
        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }

        public String getPredictedNumbers() {
            return predictedNumbers;
        }

        public void setPredictedNumbers(String predictedNumbers) {
            this.predictedNumbers = predictedNumbers;
        }

        public Boolean getNotification() {
            return notification;
        }

        public void setNotification(Boolean notification) {
            this.notification = notification;
        }
    }
}