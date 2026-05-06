package com.chaegangjo.dto;

import com.chaegangjo.group.domain.GroupChatMessage;

import java.time.LocalDateTime;

public record StompGroupChatMessageResponse(
        Long messageId,
        Long chatRoomId,
        Long senderId,
        String senderUsername,
        String message,
        LocalDateTime createdAt
) {
    public static StompGroupChatMessageResponse of(GroupChatMessage message, Long chatRoomId, Long senderId, String senderUsername) {
        return new StompGroupChatMessageResponse(
                message.getId(),
                chatRoomId,
                senderId,
                senderUsername,
                message.getMessage(),
                message.getCreatedAt()
        );
    }
}
