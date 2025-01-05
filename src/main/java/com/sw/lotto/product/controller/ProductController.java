package com.sw.lotto.product.controller;

import com.sw.lotto.global.common.controller.CommonController;
import com.sw.lotto.global.common.model.ApiResponse;
import com.sw.lotto.product.model.ProductDocument;
import com.sw.lotto.product.model.ProductListRequeset;
import com.sw.lotto.product.model.ProductListResponse;
import com.sw.lotto.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController extends CommonController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse getProductList(@RequestParam(required = false) String category, @RequestParam(required = false) String sort, @RequestParam(required = false) String searchWord,
                                      @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        ProductListRequeset plr = ProductListRequeset.builder()
                .category(category)
                .sort(sort)
                .searchWord(searchWord)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        ProductListResponse response = productService.getProductList(plr);
        return success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse getProductDetail(@RequestParam String id, @RequestParam String category) {
        ProductDocument response = productService.getProductDetail(id, category);
        return success(response);
    }

}
