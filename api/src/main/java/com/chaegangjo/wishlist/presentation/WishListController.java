package com.chaegangjo.wishlist.presentation;

import com.chaegangjo.chat.dto.request.SaveWishItemRequest;
import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.wishlist.application.ExistsInWishListUsecase;
import com.chaegangjo.wishlist.application.GetWishListUsecase;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import com.chaegangjo.wishlist.application.DeleteWishListItemUsecase;
import com.chaegangjo.wishlist.application.SaveWishListItemUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "관심상품", description = "관심상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/wishlist")
public class WishListController {

    private final GetWishListUsecase getWishListUsecase;
    private final SaveWishListItemUsecase saveWishListItemUsecase;
    private final DeleteWishListItemUsecase deleteWishListItemUsecase;
    private final ExistsInWishListUsecase existsInWishListUsecase;

    @Operation(summary = "나의 관심 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> getWishlist(
            @Parameter(example = "20", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(getWishListUsecase.execute(user.getId(), cursorId, cursorCreatedAt))
        );
    }

    @Operation(summary = "관심 상품 저장")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveWishItem(
            @Parameter(example = "1")
            @RequestBody SaveWishItemRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        saveWishListItemUsecase.execute(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "관심 상품 삭제")
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<Void>> deleteWishItem(
            @Parameter(example = "1")
            @PathVariable Long goodsId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        deleteWishListItemUsecase.execute(user.getId(), goodsId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "관심 상품 등록 여부")
    @GetMapping("/exists")
    public ResponseEntity<ApiResponse<ExistsInWishListResponse>> ExistsInWishList(
            @Parameter(example = "1", description = "상품 ID")
            @RequestParam Long goodsId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(existsInWishListUsecase.execute(user.getId(), goodsId)));
    }
}
