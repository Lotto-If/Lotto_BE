package com.sw.lotto.Lotto.ServiceTest;

import com.sw.lotto.es.lotto.dto.StatNumDto;
import com.sw.lotto.es.lotto.model.StatNumDocument;
import com.sw.lotto.es.lotto.repository.StatNumRepository;
import com.sw.lotto.es.lotto.service.StatNumService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StatNumServiceTest {

    @InjectMocks
    StatNumService statNumService;

    @Mock
    StatNumRepository statNumRepository;

    @Test
    @DisplayName("로또 통계 리스트 조회 성공")
    void getStatNumList_Success() {
        StatNumDocument stat1 = new StatNumDocument();
        stat1.setCnt(100);
        stat1.setNumber(1);

        StatNumDocument stat2 = new StatNumDocument();
        stat2.setCnt(200);
        stat2.setNumber(2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "cnt"));
        when(statNumRepository.findByYear(2025, pageable)).thenReturn(new PageImpl<>(List.of(stat2,stat1)));

        Page<StatNumDto> result = statNumService.getStatNumList(2025, "cnt", 0, 10);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .isSortedAccordingTo((a, b) -> Long.compare(b.getCnt(), a.getCnt()));

        verify(statNumRepository, times(1)).findByYear(2025, pageable);
    }

    @Test
    @DisplayName("로또 통계 리스트 조회 실패")
    void getStatNumList_notFound() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "cnt"));
        when(statNumRepository.findByYear(2024, pageable)).thenReturn(Page.empty());

        AppException exception = assertThrows(AppException.class, () -> statNumService.getStatNumList(2024, "cnt", 0, 10));

        assertEquals(ExceptionCode.NON_EXISTENT_LOTTO, exception.getExceptionCode());
        verify(statNumRepository, times(1)).findByYear(2024, pageable);
    }

}