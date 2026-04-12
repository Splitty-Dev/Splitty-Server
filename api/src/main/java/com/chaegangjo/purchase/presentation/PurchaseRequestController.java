package com.chaegangjo.purchase.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.purchase.application.GetAllPurchaseRequestsUseCase;
import com.chaegangjo.purchase.application.GetPurchaseRequestUseCase;
import com.chaegangjo.purchase.application.SavePurchaseRequestUseCase;
import com.chaegangjo.purchase.dto.request.SavePurchaseRequestRequest;
import com.chaegangjo.purchase.dto.response.PurchaseRequestInfo;
import com.chaegangjo.purchase.dto.response.PurchaseRequestSummaryInfo;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.List;

@Tag(name = "구매 요청 (부탁해요)", description = "물품 구매 요청 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/purchase-requests")
public class PurchaseRequestController {

    private final SavePurchaseRequestUseCase savePurchaseRequestUseCase;
    private final GetAllPurchaseRequestsUseCase getAllPurchaseRequestsUseCase;
    private final GetPurchaseRequestUseCase getPurchaseRequestUseCase;

    @Operation(summary = "구매 요청 목록 조회", description = "부탁해요 목록을 최신순으로 조회합니다. 커서 기반 페이지네이션.")
    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageResponse<List<PurchaseRequestSummaryInfo>>>> getAllPurchaseRequests(
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId
    ) {
        return ResponseEntity.ok(ApiResponse.success(getAllPurchaseRequestsUseCase.execute(cursorId)));
    }

    @Operation(summary = "구매 요청 상세 조회", description = "부탁해요 상세 정보를 조회합니다. 요청자의 프로필, 지역, 신뢰도 포함.")
    @GetMapping("/{purchaseRequestId}")
    public ResponseEntity<ApiResponse<PurchaseRequestInfo>> getPurchaseRequest(
            @Parameter(example = "1")
            @PathVariable Long purchaseRequestId
    ) {
        return ResponseEntity.ok(ApiResponse.success(getPurchaseRequestUseCase.execute(purchaseRequestId)));
    }

    @Operation(summary = "구매 요청 등록", description = "물품 구매를 대신 해줄 사람을 찾는 요청을 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseRequestInfo>> savePurchaseRequest(
            @RequestBody SavePurchaseRequestRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(savePurchaseRequestUseCase.execute(request, user.getId())));
    }
}
