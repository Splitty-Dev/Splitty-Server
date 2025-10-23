package com.chaegangjo.chat.dto;

import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.MessageType;
import com.chaegangjo.trade.domain.TradeMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

public record ChatMessageInfo(Long id,
                              Long senderId,
                              @Schema(description = "메시지 타입", example = "TEXT")
                              MessageType type,
                              @Schema(example = "안녕하세요.")
                              String message,
                              @Schema(example = "2024-10-15T10:20:30.123")
                              LocalDateTime createdAt) {

    @Builder
    public ChatMessageInfo(Long id, Long senderId, MessageType type, String message, LocalDateTime createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static ChatMessageInfo from(ChatMessage chatMessage) {
        TradeMember tradeMember = chatMessage.getTradeMember();
        Long senderId = tradeMember.getId();

        return ChatMessageInfo.builder()
                .id(chatMessage.getId())
                .senderId(senderId)
                .type(chatMessage.getType())
                .message(chatMessage.getMessage())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
