package com.chaegangjo.chat.presentation;

import com.chaegangjo.chat.application.GetChatListUsecase;
import com.chaegangjo.chat.application.GetChatMessagesUsecase;
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

    private final GetChatMessagesUsecase getChatMessagesUsecase;
    private final GetChatListUsecase getChatListUsecase;

    @Operation(summary = "채팅 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatInfo>>> getChatList(
            @AuthenticationPrincipal
            CustomOAuth2User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(getChatListUsecase.execute(user.getId())));
    }

    @Operation(summary = "채팅 메시지 조회")
    @GetMapping("/{tradeId}")
    public ResponseEntity<ApiResponse<CursorPageResponse<ChatMessagesInfo>>> getChatMessages(
            @Parameter(example = "1")
            @PathVariable Long tradeId,
            @Parameter(example = "10", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastId 값")
            @RequestParam(required = false) Long cursorId,
            @Parameter(example = "2025-10-12T14:51:24.999", description = "첫 요청 시 null, 이후에는 response의 nextCursor.lastCreatedAt 값")
            @RequestParam(required = false) LocalDateTime cursorCreatedAt
            ) {

        return ResponseEntity.ok(ApiResponse.success(getChatMessagesUsecase.execute(tradeId, cursorId, cursorCreatedAt)));
    }
}
