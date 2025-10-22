package com.chaegangjo.goods.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.goods.application.GetDetailGoodsUsecase;
import com.chaegangjo.goods.dto.response.GoodsCursorPageResponse;
import com.chaegangjo.goods.dto.response.DetailGoodsInfoResponse;
import com.chaegangjo.goods.application.GetGoodsUsecase;
import com.chaegangjo.goods.dto.response.GoodsInfoResponse;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "전체 상품 조회", description = "첫 요청 시 null 값으로 요청 (cursorId=null) / 이후에는 response의 nextCursor 값으로 요청")
    @GetMapping
    public ResponseEntity<ApiResponse<GoodsCursorPageResponse<List<GoodsInfoResponse>>>> getAllGoods(
            @Schema(example = "10")
            @RequestParam(required = false) Long cursorId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(getGoodsUsecase.execute(user.getId(), cursorId))
        );
    }

    @Operation(summary = "상품 조회")
    @GetMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<DetailGoodsInfoResponse>> getGoods(
            @Schema(example = "1")
            @PathVariable Long goodsId)
    {
        return ResponseEntity.ok(ApiResponse.success(getDetailGoodsUsecase.execute(goodsId)));
    }
}