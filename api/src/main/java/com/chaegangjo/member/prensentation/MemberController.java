package com.chaegangjo.member.prensentation;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.appllication.GetMemberGoodsUseCase;
import com.chaegangjo.member.appllication.GetMemberInfoUseCase;
import com.chaegangjo.member.appllication.GetMySearchHistoriesUseCase;
import com.chaegangjo.member.appllication.SaveMyFcmTokenUseCase;
import com.chaegangjo.member.appllication.SetNeighborhoodUsecase;
import com.chaegangjo.member.dto.MemberInfo;
import com.chaegangjo.member.dto.SearchHistoryInfo;
import com.chaegangjo.member.dto.request.SaveMyFcmTokenRequest;
import com.chaegangjo.member.dto.request.SetNeighborhoodRequest;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final SetNeighborhoodUsecase setNeighborhoodUsecase;
    private final GetMemberGoodsUseCase getMemberGoodsUseCase;
    private final GetMySearchHistoriesUseCase getMySearchHistoriesUseCase;
    private final SaveMyFcmTokenUseCase saveMyFcmTokenUseCase;

    @Operation(summary = "회원 정보 조회")
//    @PreAuthorize("#id == principal.id")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfo>> getMemberInfo(
            @Parameter(example = "1")
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

    @Operation(summary = "나의 지역 설정")
    @PostMapping("/me/neighborhood")
    public ResponseEntity<ApiResponse<MemberInfo>> setNeighborhood(
            @RequestBody SetNeighborhoodRequest request,
            @AuthenticationPrincipal CustomOAuth2User user) {

        return ResponseEntity.ok(
                ApiResponse.success(setNeighborhoodUsecase.execute(user.getId(), request))
        );
    }

    @Operation(summary = "회원 판매내역 조회")
    @GetMapping("/{memberId}/sales")
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> getMemberSales(
            @Parameter(example = "1")
            @PathVariable Long memberId,
            @Parameter(example = "OPEN")
            @RequestParam TradeStatus status,
            @Parameter(example = "20", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(
                ApiResponse.success(getMemberGoodsUseCase.purchased(memberId, status, cursorId, cursorCreatedAt))
        );
    }

    @Operation(summary = "나의 판매내역 조회")
    @GetMapping("/me/sales")
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> getMySales(
            @Parameter(example = "OPEN")
            @RequestParam TradeStatus status,
            @Parameter(example = "20", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(
                ApiResponse.success(getMemberGoodsUseCase.purchased(user.getId(), status, cursorId, cursorCreatedAt))
        );
    }

    @Operation(summary = "나의 구매내역 조회")
    @GetMapping("/me/purchases")
    public ResponseEntity<ApiResponse<CursorPageResponse<List<GoodsInfo>>>> getMyPurchases(
            @Parameter(example = "OPEN")
            @RequestParam TradeStatus status,
            @Parameter(example = "20", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(
                ApiResponse.success(getMemberGoodsUseCase.sold(user.getId(), status, cursorId, cursorCreatedAt))
        );
    }

    @Operation(summary = "[New] 나의 검색 기록 조회")
    @GetMapping("/me/search")
    public ResponseEntity<ApiResponse<List<SearchHistoryInfo>>> getMySearchHistories(
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(
                ApiResponse.success(getMySearchHistoriesUseCase.execute(user.getId()))
        );
    }

    @PatchMapping("/me/fcm-token")
    @Operation(summary = "[New] FCM 토큰 저장")
    public ResponseEntity<ApiResponse<Void>> saveFcmToken(
            @RequestBody SaveMyFcmTokenRequest request,
            @AuthenticationPrincipal CustomOAuth2User user) {
        saveMyFcmTokenUseCase.execute(user.getId(), request.token());
        return ResponseEntity.ok(ApiResponse.success());
    }
}