package com.sw.lotto.Lotto.ControllerTest;

import com.sw.lotto.es.lotto.controller.LottoController;
import com.sw.lotto.es.lotto.dto.LottoDto;
import com.sw.lotto.es.lotto.service.LottoService;
import com.sw.lotto.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LottoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class LottoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LottoService lottoService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    LottoDto dto1;
    LottoDto dto2;

    @BeforeEach
    void setUp() {
        dto1 = new LottoDto("1", 1000000L, "2025-01-01", 1153, 1, 5000000L, "1,2,3,4,5,6");
        dto2 = new LottoDto("2", 1000000L, "2025-01-01", 1154, 2, 5000000L, "1,2,3,4,5,6");
    }

    @Test
    @DisplayName("로또 리스트 반환 받기")
    void getLottoList_success() throws Exception {

        Page<LottoDto> page = new PageImpl<>(List.of(dto1, dto2));
        when(lottoService.getLottoList(anyInt(), anyInt(), anyString())).thenReturn(page);

        mvc.perform(get("/api/lotto")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "round"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].round").value(1153))
                .andExpect(jsonPath("$.content[1].round").value(1154));
    }

    @Test
    @DisplayName("회차별 로또 반환 받기")
    void getLottoByRound_success() throws Exception {
        when(lottoService.getLottoByRound(1154)).thenReturn(dto2);

        mvc.perform(get("/api/lotto/1154"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.round").value(1154));
    }


    @Test
    @DisplayName("유효하지 않은 페이지 번호 넘기기")
    public void testInvalidPageNumber() throws Exception {
        mvc.perform(get("/api/lotto")
                        .param("page", "-1")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultMessage").value("getAllLotto.page: 페이지 번호는 0 이상이어야 합니다."));
    }

    @Test
    @DisplayName("유효하지 않은 페이지 사이즈 넘기기")
    public void testInvalidPageSize() throws Exception {
        mvc.perform(get("/api/lotto")
                        .param("page", "0")
                        .param("size", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultMessage").value("getAllLotto.size: 페이지 크기는 1 이상이어야 합니다."));
    }

    @Test
    @DisplayName("유효하지 않은 회차 정보 넘기기")
    public void testInvalidRoundNumber() throws Exception {
        mvc.perform(get("/api/lotto/1152")
                        .param("round", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultMessage").value("getLottoByRound.round: 회차 번호는 1153 이상이어야 합니다."));
    }
}
