package com.chaegangjo.trade.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import com.chaegangjo.trade.application.ChangeTradeStatusUseCase;
import com.chaegangjo.trade.application.ConfirmTradeQuantitiesUseCase;
import com.chaegangjo.trade.application.GetTradeQuantitiesUseCase;
import com.chaegangjo.trade.application.IsJoinTradeUseCase;
import com.chaegangjo.trade.application.JoinTradeUseCase;
import com.chaegangjo.trade.dto.ChangeTradeStatusRequest;
import com.chaegangjo.trade.dto.ConfirmTradeQuantityRequest;
import com.chaegangjo.trade.dto.GetTradeQuantitiesResponse;
import com.chaegangjo.trade.dto.JoinTradeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "거래", description = "거래 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

    private final JoinTradeUseCase joinTradeUsecase;
    private final IsJoinTradeUseCase isJoinTradeUsecase;
    private final ChangeTradeStatusUseCase changeTradeStatusUsecase;
    private final ConfirmTradeQuantitiesUseCase confirmTradeQuantitiesUsecase;
    private final GetTradeQuantitiesUseCase getTradeQuantitiesUsecase;

    @Operation(summary = "거래 참여")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> joinTrade(
            @RequestBody JoinTradeRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        joinTradeUsecase.execute(request, user.getId());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "거래 참여 여부")
    @PostMapping("/is-joined")
    public ResponseEntity<ApiResponse<IsJoinedTradeResponse>> isJoinedTrade(
            @Parameter(example = "1")
            @RequestParam Long goodsId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(isJoinTradeUsecase.execute(user.getId(), goodsId)));
    }

    @Operation(summary = "거래 상태 변경")
    @PatchMapping("/change-status")
    public ResponseEntity<ApiResponse<Void>> changeTradeStatus(
            @RequestBody ChangeTradeStatusRequest request,
            @AuthenticationPrincipal CustomOAuth2User user) {
        changeTradeStatusUsecase.execute(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "거래 수량 조회")
    @GetMapping("/{goodsId}/quantity")
    public ResponseEntity<ApiResponse<GetTradeQuantitiesResponse>> getTradeQuantity(
            @Parameter(example = "1")
            @PathVariable Long goodsId
    ) {
        return ResponseEntity.ok(ApiResponse.success(getTradeQuantitiesUsecase.execute(goodsId)));
    }

    @Operation(summary = "거래 수량 확정")
    @PatchMapping("/confirm-quantity")
    public ResponseEntity<ApiResponse<Void>> confirmTradeQuantity(
            @RequestBody ConfirmTradeQuantityRequest request,
            @AuthenticationPrincipal CustomOAuth2User user) {
        confirmTradeQuantitiesUsecase.execute(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
