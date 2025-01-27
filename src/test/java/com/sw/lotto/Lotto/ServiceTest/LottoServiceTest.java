package com.sw.lotto.Lotto.ServiceTest;

import com.sw.lotto.es.lotto.dto.LottoDto;
import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import com.sw.lotto.es.lotto.service.LottoService;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LottoServiceTest {

    @InjectMocks
    LottoService lottoService;

    @Mock
    LottoRepository lottoRepository;

    private LottoDocument lotto1;
    private LottoDocument lotto2;

    @BeforeEach
    void setUp() {
        lotto1 = new LottoDocument();
        lotto1.setRound(1);

        lotto2 = new LottoDocument();
        lotto2.setRound(2);
    }

    @Test
    @DisplayName("전체 로또 리스트 조회 성공")
    void getLottoList_success() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "round"));
        when(lottoRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(lotto1,lotto2)));

        Page<LottoDto> result = lottoService.getLottoList(0, 10, "round");

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        verify(lottoRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("특정 회차 로또 조회 성공")
    void getLottoByRound_success() {
        when(lottoRepository.findByRound(1)).thenReturn(Optional.of(lotto1));

        LottoDto result = lottoService.getLottoByRound(1);

        assertThat(result).isNotNull();
        assertThat(result.getRound()).isEqualTo(1);
        verify(lottoRepository, times(1)).findByRound(1);
    }

    @Test
    @DisplayName("특정 회차 로또 조회 실패")
    void getLottoByRound_notFound() {
        when(lottoRepository.findByRound(3)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> lottoService.getLottoByRound(3));

        assertThat(exception).isNotNull();
        assertThat(exception.getExceptionCode()).isEqualTo(ExceptionCode.NON_EXISTENT_LOTTO);
        verify(lottoRepository, times(1)).findByRound(3);
    }

}