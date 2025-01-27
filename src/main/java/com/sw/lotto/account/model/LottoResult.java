package com.sw.lotto.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LottoResult {
    private final List<Integer> mainNumbers; // 로또의 메인 번호 (6개)
    private final Integer bonusNumber;      // 로또의 보너스 번호
}
