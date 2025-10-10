package com.chaegangjo.goods.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.pagination.CursorPageInfo;
import com.chaegangjo.goods.application.GetGoodsUsecase;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "상품", description = "상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/goods")
public class GoodsController {

    private final GetGoodsUsecase getGoodsUsecase;

    @Operation(summary = "전체 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageInfo<List<GoodsInfo>>>> getGoods(
            @RequestParam Long cursorId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(getGoodsUsecase.execute(user.getId(), cursorId))
        );
    }
}