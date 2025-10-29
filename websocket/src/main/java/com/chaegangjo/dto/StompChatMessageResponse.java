package com.chaegangjo.dto;

import com.chaegangjo.trade.domain.ChatMessage;
import java.time.LocalDateTime;

public record StompChatMessageResponse(
        Long messageId,
        Long tradeId,
        Long senderId,
        String senderUsername,
        String message,
        LocalDateTime createdAt
){

    public static StompChatMessageResponse of(ChatMessage chatMessage, Long tradeId, Long senderId, String senderUsername) {

        return new StompChatMessageResponse(
                chatMessage.getId(),
                tradeId,
                senderId,
                senderUsername,
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }
}