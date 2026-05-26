package com.chaegangjo.product.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.product.application.SearchProductUseCase;
import com.chaegangjo.product.dto.ProductInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "상품 DB", description = "코스트코 상품 DB 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final SearchProductUseCase searchProductUseCase;

    @Operation(summary = "상품 자동완성", description = "물품 등록 시 상품명 입력 자동완성 (최대 7개)")
    @GetMapping("/autocomplete")
    public ResponseEntity<ApiResponse<List<ProductInfo>>> autocomplete(
            @Parameter(example = "코카콜라", description = "검색어 (2글자 이상 권장)")
            @RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success(searchProductUseCase.execute(keyword)));
    }
}
