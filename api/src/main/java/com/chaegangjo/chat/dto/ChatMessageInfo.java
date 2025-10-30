package com.chaegangjo.chat.dto;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.enums.MessageType;
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
        ChatMember chatMember = chatMessage.getChatMember();
        Long senderId = chatMember.getId();
        String message = chatMessage.getMessage();
        if (chatMessage.getType() == MessageType.ENTER || chatMessage.getType() == MessageType.LEAVE) {
            message = chatMember.getUsername() + chatMessage.getType().getMessage();
        }
        return ChatMessageInfo.builder()
                .id(chatMessage.getId())
                .senderId(senderId)
                .type(chatMessage.getType())
                .message(message)
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
