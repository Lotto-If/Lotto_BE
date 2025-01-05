package com.sw.lotto.product.service;

import com.sw.lotto.car.model.CarDocument;
import com.sw.lotto.car.service.CarService;
import com.sw.lotto.global.common.model.BaseEnum;
import com.sw.lotto.global.common.model.ResultCode;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.luxury.model.LuxuryDocument;
import com.sw.lotto.luxury.service.LuxuryService;
import com.sw.lotto.product.model.*;
import com.sw.lotto.realEstate.model.RealEstateDocument;
import com.sw.lotto.realEstate.service.RealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sw.lotto.global.exception.ExceptionCode.NON_EXISTENT_PRODUCT_CATEGORY;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CarService carService;
    private final RealEstateService realEstateService;
    private final LuxuryService luxuryService;

    /**
     * 상품 목록 조회
     * @param plr
     * plr.category 상품 카테고리 (CAR, REAL_ESTATE, LUXURY) , 기본값 ALL 이면 전체 조회
     * plr.sort 정렬 기준 (PRICE_LOW, PRICE_HIGH), 기본값 DEFAULT 이면 기본 정렬
     * plr.searchWord 검색어
     * plr.pageNumber, plr.pageSize 페이지네이션
     * @return List<ProductDoucment>
     */
    public ProductListResponse getProductList(ProductListRequeset plr) {
        ProductCategory productCategory;
        ProductSort productSort;
        Sort sort = null;

        try {
            productCategory = BaseEnum.fromValue(ProductCategory.class, plr.getCategory());
            productSort = BaseEnum.fromValue(ProductSort.class, plr.getSort());
        } catch (IllegalArgumentException e) {
            throw new AppException(ResultCode.FAILURE,e.getMessage());
        }

        if (productSort != null){
            switch (productSort) {
                case PRICE_LOW -> {
                    sort = Sort.by(Sort.Order.asc("price"));
                }
                case PRICE_HIGH -> {
                    sort = Sort.by(Sort.Order.desc("price"));
                }
            }
        }

        Pageable pageable = sort==null ? PageRequest.of(plr.getPageNumber(), plr.getPageSize()) : PageRequest.of(plr.getPageNumber(), plr.getPageSize(), sort);

        if (productCategory != null) {
            switch (productCategory) {
                case CAR -> {
                    Page<CarDocument> carDocuments = carService.getCarsWithSearchWordAndPageable(plr.getSearchWord(), pageable);

                    // CarDocument를 ProductDocument로 변환
                    List<ProductDocument> productDocuments = carDocuments.stream()
                            .map(carDocument -> (ProductDocument) carDocument) // 적절한 변환 로직
                            .collect(Collectors.toList());

                    return ProductListResponse.builder()
                            .contents(productDocuments)
                            .totalPages(carDocuments.getTotalPages())
                            .totalElements(carDocuments.getTotalElements())
                            .build();
                }
                case REAL_ESTATE -> {
                    Page<RealEstateDocument> realEstateDocuments = realEstateService.getRealEstatesWithSearchWordAndPageable(plr.getSearchWord(), pageable);

                    // RealEstateDocument를 ProductDocument로 변환
                    List<ProductDocument> productDocuments = realEstateDocuments.stream()
                            .map(realEstateDocument -> (ProductDocument) realEstateDocument) // 적절한 변환 로직
                            .toList();

                    return ProductListResponse.builder()
                            .contents(productDocuments)
                            .totalPages(realEstateDocuments.getTotalPages())
                            .totalElements(realEstateDocuments.getTotalElements())
                            .build();
                }
                case LUXURY -> {
                    Page<LuxuryDocument> luxuryDocuments = luxuryService.getLuxuriesWithSearchWordAndPageable(plr.getSearchWord(), pageable);

                    // LuxuryDocument를 ProductDocument로 변환
                    List<ProductDocument> productDocuments = luxuryDocuments.stream()
                            .map(luxuryDocument -> (ProductDocument) luxuryDocument) // 적절한 변환 로직
                            .toList();

                    return ProductListResponse.builder()
                            .contents(productDocuments)
                            .totalPages(luxuryDocuments.getTotalPages())
                            .totalElements(luxuryDocuments.getTotalElements())
                            .build();
                }
            }
        }

        // 전체 조회
        return getAllProductList(productSort, plr.getSearchWord(), plr.getPageNumber(), plr.getPageSize());
    }

    private ProductListResponse getAllProductList(ProductSort productSort, String searchWord, int pageNumber, int pageSize) {
        // elasticsearch multisearch를 사용하여 전체 상품 목록 조회
        List<CarDocument> cars = carService.getCarsWithSearchWord(searchWord);
        List<RealEstateDocument> realEstates = realEstateService.getRealEstatesWithSearchWord(searchWord);
        List<LuxuryDocument> luxuries = luxuryService.getLuxuriesWithSearchWord(searchWord);

        // CarDocument, RealEstateDocument, LuxuryDocument를 ProductDocument로 변환
        List<ProductDocument> productDocuments = new ArrayList<>(cars.stream()
                .map(carDocument -> (ProductDocument) carDocument) // 적절한 변환 로직
                .toList());
        productDocuments.addAll(realEstates.stream()
                .map(realEstateDocument -> (ProductDocument) realEstateDocument) // 적절한 변환 로직
                .toList());
        productDocuments.addAll(luxuries.stream()
                .map(luxuryDocument -> (ProductDocument) luxuryDocument) // 적절한 변환 로직
                .toList());

        // 상품 목록을 정렬한다.
        if (productSort != null) {
            switch (productSort) {
                case PRICE_LOW -> productDocuments.sort(Comparator.comparing(ProductDocument::getPrice));
                case PRICE_HIGH -> productDocuments.sort(Comparator.comparing(ProductDocument::getPrice).reversed());
            }
        }

        // 페이지네이션 적용.
        int start = pageNumber * pageSize;
        int end = Math.min((start + pageSize), productDocuments.size());
        List<ProductDocument> paginatedList = productDocuments.subList(start, end);

        // 상품 목록을 반환한다.
        return ProductListResponse.builder()
                .contents(paginatedList)
                .totalPages((int) Math.ceil((double) productDocuments.size() / pageSize))   // 올림 처리
                .totalElements(productDocuments.size())
                .build();
    }

    /**
     * 개별 상품 조회
     * @param id 상품 ID
     * @param category 상품 카테고리 (CAR, REAL_ESTATE, LUXURY)
     * @return ProductDoucment
     */
    public ProductDocument getProductDetail(String id, String category) {
        ProductCategory productCategory;

        try {
            productCategory = BaseEnum.fromValue(ProductCategory.class, category);
        } catch (IllegalArgumentException e) {
            throw new AppException(ResultCode.FAILURE,e.getMessage());
        }

        switch (productCategory) {
            case CAR -> {
                return carService.getCarDetail(id);
            }
            case REAL_ESTATE -> {
                return realEstateService.getRealEstateDetail(id);
            }
            case LUXURY -> {
                return luxuryService.getLuxuryDetail(id);
            }
            default -> throw new AppException(NON_EXISTENT_PRODUCT_CATEGORY);
        }
    }
}
