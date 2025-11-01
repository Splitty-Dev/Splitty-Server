package com.chaegangjo.goods.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.application.GetGoodsUseCase;
import com.chaegangjo.goods.application.GetAllGoodsUseCase;
import com.chaegangjo.goods.application.SaveGoodsUseCase;
import com.chaegangjo.goods.application.SearchGoodsUseCase;
import com.chaegangjo.goods.dto.DetailGoodsInfo;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.dto.request.SaveGoodsRequest;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품", description = "상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/goods")
public class GoodsController {

    private final GetAllGoodsUseCase getAllGoodsUsecase;
    private final GetGoodsUseCase getGoodsUsecase;
    private final SaveGoodsUseCase saveGoodsUsecase;
    private final SearchGoodsUseCase searchGoodsUsecase;

    @Operation(summary = "전체 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> getAllGoods(
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "0", description = "전체 조회 시 0, 카테고리별 조회 시 카테고리 ID")
            @RequestParam Long categoryId,
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(
                ApiResponse.success(getAllGoodsUsecase.executeWithRecommendation(user.getId(), categoryId, cursorId))
        );
    }

    @Operation(summary = "상품 조회")
    @GetMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<DetailGoodsInfo>> getGoods(
            @Parameter(example = "1")
            @PathVariable Long goodsId) {
        return ResponseEntity.ok(ApiResponse.success(getGoodsUsecase.detail(goodsId)));
    }

    @Operation(summary = "상품 요약 조회", description = "채팅방/거래 수량 확정 페이지 등 상단 노출")
    @GetMapping("/{goodsId}/summary")
    public ResponseEntity<ApiResponse<GoodsInfo>> getSimpleGoods(
            @Parameter(example = "1")
            @PathVariable Long goodsId) {
        return ResponseEntity.ok(ApiResponse.success(getGoodsUsecase.simple(goodsId)));
    }

    @Operation(summary = "상품 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<DetailGoodsInfo>> saveGoods(
            @RequestBody SaveGoodsRequest request,
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(ApiResponse.success(saveGoodsUsecase.execute(request, user.getId())));
    }

    @Operation(summary = "상품 검색 조회(제목/내용 기반)")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> searchGoods(
            @Parameter(example = "생수", description = "검색어")
            @RequestParam String keyword,
            @Parameter(example = "20", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-11-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(ApiResponse.success(searchGoodsUsecase.execute(user.getId(), keyword, cursorId, cursorCreatedAt)));
    }
}