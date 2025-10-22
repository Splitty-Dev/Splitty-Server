package com.chaegangjo.chat.presentation;

import com.chaegangjo.chat.application.GetChatMessagesUsecase;
import com.chaegangjo.chat.dto.ChatMessagesInfo;
import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "채팅", description = "채팅 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    private final GetChatMessagesUsecase getChatMessagesUsecase;

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
