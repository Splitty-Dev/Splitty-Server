package com.chaegangjo.trade.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import com.chaegangjo.trade.application.ChangeTradeStatusUsecase;
import com.chaegangjo.trade.application.IsJoinTradeUsecase;
import com.chaegangjo.trade.application.JoinTradeUsecase;
import com.chaegangjo.trade.dto.JoinTradeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
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

    private final JoinTradeUsecase joinTradeUsecase;
    private final IsJoinTradeUsecase isJoinTradeUsecase;
    private final ChangeTradeStatusUsecase changeTradeStatusUsecase;

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
    @PatchMapping("/{goodsId}/status")
    public ResponseEntity<ApiResponse<Void>> changeTradeStatus(
            @Parameter(example = "1")
            @RequestParam Long goodsId,
            @Parameter(example = "COMPLETED")
            @RequestParam TradeStatus status,
            @AuthenticationPrincipal CustomOAuth2User user) {
        changeTradeStatusUsecase.execute(user.getId(), goodsId, status);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
