package com.chaegangjo.goods.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.application.GetCategoriesUsecase;
import com.chaegangjo.goods.application.GetDetailGoodsUsecase;
import com.chaegangjo.goods.application.SaveGoodsUsecase;
import com.chaegangjo.goods.dto.request.SaveGoodsRequest;
import com.chaegangjo.goods.dto.DetailGoodsInfo;
import com.chaegangjo.goods.application.GetGoodsUsecase;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품", description = "상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/goods")
public class GoodsController {

    private final GetGoodsUsecase getGoodsUsecase;
    private final GetDetailGoodsUsecase getDetailGoodsUsecase;
    private final SaveGoodsUsecase saveGoodsUsecase;

    @Operation(summary = "전체 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> getAllGoods(
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "0", description = "전체 조회 시 0, 카테고리별 조회 시 카테고리 ID")
            @RequestParam Long categoryId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(getGoodsUsecase.execute(user.getId(), categoryId, cursorId))
        );
    }

    @Operation(summary = "상품 조회")
    @GetMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<DetailGoodsInfo>> getGoods(
            @Parameter(example = "1")
            @PathVariable Long goodsId)
    {
        return ResponseEntity.ok(ApiResponse.success(getDetailGoodsUsecase.execute(goodsId)));
    }

    @Operation(summary = "상품 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<DetailGoodsInfo>> saveGoods(
            @RequestBody SaveGoodsRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(saveGoodsUsecase.execute(request, user.getId())));
    }
}