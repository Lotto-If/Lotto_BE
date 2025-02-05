package com.sw.lotto.Lotto.ControllerTest;

import com.sw.lotto.es.lotto.controller.StatNumController;
import com.sw.lotto.es.lotto.dto.StatNumResponseDto;
import com.sw.lotto.es.lotto.service.StatNumService;
import com.sw.lotto.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatNumController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class StatNumControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    StatNumService statNumService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    StatNumResponseDto dto1;
    StatNumResponseDto dto2;

    @BeforeEach
    void setUp(){
        dto1 = new StatNumResponseDto("1",100,13,2024);
        dto2 = new StatNumResponseDto("2",200,7,2024);
    }

    @Test
    @DisplayName("로또 통계 리스트 반환 받기")
    void getStatNumList_Success() throws Exception {
        Page<StatNumResponseDto> page = new PageImpl<>(List.of(dto2, dto1));
        when(statNumService.getStatNumList(anyInt(), anyString(), anyInt(), anyInt())).thenReturn(page);

        mvc.perform(get("/api/lotto/statNum")
                        .param("year","2024")
                        .param("sortBy","cnt")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cnt").value(200))
                .andExpect(jsonPath("$.content[1].cnt").value(100));
    }

    @Test
    @DisplayName("유효하지 않은 페이지 번호 넘기기")
    public void testInvalidPageNumber() throws Exception {
        mvc.perform(get("/api/lotto/statNum")
                        .param("year","2024")
                        .param("sortBy","cnt")
                        .param("page","-1")
                        .param("size","10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultMessage").value("getStatNum.page: 페이지 번호는 0 이상이어야 합니다."));
    }

    @Test
    @DisplayName("유효하지 않은 페이지 사이즈 넘기기")
    public void testInvalidPageSize() throws Exception {
        mvc.perform(get("/api/lotto/statNum")
                        .param("year","2024")
                        .param("sortBy","cnt")
                        .param("page","0")
                        .param("size","0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultMessage").value("getStatNum.size: 페이지 크기는 1 이상이어야 합니다."));
    }

    @Test
    @DisplayName("유효하지 않은 년도 값 넘기기")
    public void testInvalidRoundNumber() throws Exception {
        mvc.perform(get("/api/lotto/statNum")
                        .param("year","2023")
                        .param("sortBy","cnt")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultMessage").value("getStatNum.year: 년도는 2024 이상이어야 합니다."));
    }
}