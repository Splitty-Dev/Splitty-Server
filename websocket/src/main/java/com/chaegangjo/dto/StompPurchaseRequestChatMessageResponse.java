package com.chaegangjo.dto;

import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;

import java.time.LocalDateTime;

public record StompPurchaseRequestChatMessageResponse(
        Long messageId,
        Long chatRoomId,
        Long senderId,
        String senderUsername,
        String message,
        LocalDateTime createdAt
) {

    public static StompPurchaseRequestChatMessageResponse of(PurchaseRequestChatMessage chatMessage,
                                                              Long chatRoomId, Long senderId, String senderUsername) {
        return new StompPurchaseRequestChatMessageResponse(
                chatMessage.getId(),
                chatRoomId,
                senderId,
                senderUsername,
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }
}
