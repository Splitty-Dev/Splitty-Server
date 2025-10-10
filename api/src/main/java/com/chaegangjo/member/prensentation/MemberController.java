package com.chaegangjo.member.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.member.appllication.GetMemberInfoUseCase;
import com.chaegangjo.member.appllication.GetWishListUsecase;
import com.chaegangjo.member.appllication.SaveWishListUsecase;
import com.chaegangjo.member.appllication.SetNeighborhoodUsecase;
import com.chaegangjo.member.dto.request.GetWishList;
import com.chaegangjo.member.dto.request.SetNeighborhood;
import com.chaegangjo.member.dto.response.MemberInfo;
import com.chaegangjo.pagination.CursorPageInfo;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "회원", description = "회원 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final GetWishListUsecase getWishListUsecase;
    private final SaveWishListUsecase saveWishListUsecase;

    @Operation(summary = "회원 정보 조회")
//    @PreAuthorize("#id == principal.id")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfo>> getMemberInfo(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                ApiResponse.success(getMemberInfoUseCase.execute(memberId))
        );
    }

    @Operation(summary = "나의 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfo>> getMyInfo(
            @AuthenticationPrincipal CustomOAuth2User user) {

        return ResponseEntity.ok(
                ApiResponse.success(getMemberInfoUseCase.execute(user.getEmail()))
        );
    }

    @Operation(summary = "나의 관심 상품 조회")
    @GetMapping("/wishlist")
    public ResponseEntity<ApiResponse<CursorPageInfo<List<GoodsInfo>>>> getWishlist(
            GetWishList request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(getWishListUsecase.execute(user.getId(), request))
        );
    }

    @Operation(summary = "관심 상품 저장")
    @PostMapping("/wishlist/{goodsId}")
    public ResponseEntity<ApiResponse<Void>> saveWishlist(
            @RequestParam Long goodsId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        saveWishListUsecase.execute(user.getEmail(), goodsId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}