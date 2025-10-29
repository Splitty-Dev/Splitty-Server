package com.chaegangjo.goods.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.goods.application.GetCategoriesUsecase;
import com.chaegangjo.goods.dto.CategoryInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리", description = "카테고리 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryController {

    private final GetCategoriesUsecase getCategoriesUsecase;

    @Operation(summary = "전체 카테고리 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryInfo>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(getCategoriesUsecase.execute()));
    }
}