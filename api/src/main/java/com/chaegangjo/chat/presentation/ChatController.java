package com.chaegangjo.chat.presentation;

import com.chaegangjo.chat.enums.TradeRole;
import com.chaegangjo.chat.application.GetChatListUseCase;
import com.chaegangjo.chat.application.GetChatMessagesUseCase;
import com.chaegangjo.chat.dto.ChatInfo;
import com.chaegangjo.chat.dto.ChatMessagesInfo;
import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "채팅", description = "채팅 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    private final GetChatMessagesUseCase getChatMessagesUsecase;
    private final GetChatListUseCase getChatListUsecase;
    private final GetPurchaseRequestChatListUseCase getPurchaseRequestChatListUseCase;
    private final GetPurchaseRequestChatMessagesUseCase getPurchaseRequestChatMessagesUseCase;
    private final StartPurchaseRequestChatUseCase startPurchaseRequestChatUseCase;
    private final GetGroupChatMessagesUseCase getGroupChatMessagesUseCase;

    @Operation(summary = "같이 사요 채팅 목록 조회", description = "물품 공동구매(같이 사요) 채팅 목록 전체 조회.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatInfo>>> getChatList(
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(getChatListUsecase.execute(user.getId())));
    }

    @Operation(summary = "같이 사요 채팅 메시지 조회")
    @GetMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<CursorPageResponse<ChatMessagesInfo>>> getChatMessages(
            @Parameter(example = "1")
            @PathVariable Long goodsId,
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt
    ) {
        return ResponseEntity.ok(ApiResponse.success(getChatMessagesUsecase.execute(goodsId, cursorId, cursorCreatedAt)));
    }

    @Operation(summary = "부탁해요 채팅 시작/조회", description = "구매 요청(부탁해요)에 채팅을 시작합니다. 이미 채팅방이 존재하면 기존 채팅방 정보를 반환합니다.")
    @PostMapping("/purchase-requests")
    public ResponseEntity<ApiResponse<StartPurchaseRequestChatResponse>> startPurchaseRequestChat(
            @Parameter(description = "구매 요청 ID", example = "5")
            @RequestParam Long purchaseRequestId,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                startPurchaseRequestChatUseCase.execute(purchaseRequestId, user.getId())));
    }

    @Operation(summary = "부탁해요 채팅 목록 조회", description = "물품 구매 요청(부탁해요) 채팅 목록을 조회합니다.")
    @GetMapping("/purchase-requests")
    public ResponseEntity<ApiResponse<List<PurchaseRequestChatInfo>>> getPurchaseRequestChatList(
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(getPurchaseRequestChatListUseCase.execute(user.getId())));
    }
    @Operation(summary = "부탁해요 채팅 메시지 조회")
    @GetMapping("/purchase-requests/{chatRoomId}")
    public ResponseEntity<ApiResponse<CursorPageResponse<PurchaseRequestChatMessagesInfo>>> getPurchaseRequestChatMessages(
            @Parameter(example = "10")
            @PathVariable Long chatRoomId,
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                getPurchaseRequestChatMessagesUseCase.execute(chatRoomId, user.getId(), cursorId, cursorCreatedAt)));
    }
}
