package com.chaegangjo.wishlist.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.member.appllication.GetWishListUsecase;
import com.chaegangjo.wishlist.dto.request.GetWishList;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import com.chaegangjo.wishlist.application.DeleteWishListItemUsecase;
import com.chaegangjo.wishlist.application.SaveWishListItemUsecase;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관심상품", description = "관심상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/wishlist")
public class WishListController {

    private final GetWishListUsecase getWishListUsecase;
    private final SaveWishListItemUsecase saveWishListItemUsecase;
    private final DeleteWishListItemUsecase deleteWishListItemUsecase;

    @Operation(summary = "나의 관심 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<WishListCursorPageInfo<List<GoodsInfo>>>> getWishlist(
            GetWishList request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(getWishListUsecase.execute(user.getId(), request))
        );
    }

    @Operation(summary = "관심 상품 저장")
    @PostMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<Void>> saveWishItem(
            @Schema(example = "1")
            @RequestParam Long goodsId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        saveWishListItemUsecase.execute(user.getEmail(), goodsId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "관심 상품 삭제")
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<Void>> deleteWishItem(
            @Schema(example = "1")
            @RequestParam Long goodsId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        deleteWishListItemUsecase.execute(user.getEmail(), goodsId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
