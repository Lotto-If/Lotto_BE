package com.sw.lotto.product.controller;

import com.sw.lotto.account.service.AccountService;
import com.sw.lotto.auth.controller.AuthController;
import com.sw.lotto.auth.service.RefreshTokenService;
import com.sw.lotto.luxury.model.LuxuryDocument;
import com.sw.lotto.product.model.ProductCategory;
import com.sw.lotto.product.model.ProductDocument;
import com.sw.lotto.product.service.ProductService;
import com.sw.lotto.security.jwt.util.JwtTokenUtil;
import com.sw.lotto.security.jwt.util.TokenManager;
import com.sw.lotto.security.service.CustomAuthenticationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.sw.lotto.product.model.ProductCategory.LUXURY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private TokenManager tokenManager;
    @MockBean
    private AccountService accountService;
    @MockBean
    private CustomAuthenticationProvider authenticationManager;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("상품 상세 정보 조회 성공 테스트")
    void getProductDetail_success() throws Exception {
        // given
        String pid = "pid";
        ProductDocument pd = new LuxuryDocument(pid, "Product Name", "P001", 1000L, "imageURL", "brand", "opt", "sex");
        when(productService.getProductDetail(pid, LUXURY.getValue())).thenReturn(pd);

        // when & then
        mockMvc.perform(get("/api/product/{id}", pid)
                        .param("category", LUXURY.getValue())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.pid").value(pid))
                .andExpect(jsonPath("$.data.pname").value("Product Name"))
                .andExpect(jsonPath("$.data.price").value(1000L));
    }
}