package com.sw.lotto.account.controller;

import com.sw.lotto.account.model.UserLottoRequestDto;
import com.sw.lotto.account.model.UserLottoResponseDto;
import com.sw.lotto.account.service.UserLottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-lotto")
@RequiredArgsConstructor
public class UserLottoController {

    private final UserLottoService userLottoService;

    @PostMapping
    public ResponseEntity<UserLottoResponseDto> createUserLotto(
            @RequestBody UserLottoRequestDto userLottoRequestDto) {
        UserLottoResponseDto savedDto = userLottoService.saveUserLotto(userLottoRequestDto);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping
    public ResponseEntity<?> getUserLotto(
            @RequestParam(required = false) Integer round) {
        if (round != null) {
            UserLottoResponseDto userLotto = userLottoService.getUserLottoByRound(round);
            return ResponseEntity.ok(userLotto);
        } else {
            List<UserLottoResponseDto> userLottos = userLottoService.getUserLottoByAccount();
            return ResponseEntity.ok(userLottos);
        }
    }

    @PostMapping("/check-winnings")
    public ResponseEntity<?> checkWinnings() {
        userLottoService.checkWinningsAndNotify();
        return ResponseEntity.ok("Winnings checked and notifications sent.");
    }

    @DeleteMapping("/{userLottoOid}")
    public ResponseEntity<?> removeUserLotto(
            @PathVariable Long userLottoOid){
        userLottoService.removeUserLotto(userLottoOid);
        return ResponseEntity.ok("Item successfully removed.");
    }

    @PutMapping("/{userLottoOid}")
    public ResponseEntity<?> updateUserLotto(
            @PathVariable Long userLottoOid,
            @RequestParam String predictedNumbers){
        userLottoService.updateUserLotto(userLottoOid,predictedNumbers);
        return ResponseEntity.ok("Item successfully updated.");
    }
}
