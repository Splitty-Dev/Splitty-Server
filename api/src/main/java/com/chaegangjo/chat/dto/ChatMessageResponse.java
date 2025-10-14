package com.chaegangjo.chat.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.TradeMember;

import java.time.LocalDateTime;

public record ChatMessageResponse (
        Long messageId,
        Long tradeId,
        Long senderId,
        String senderUsername,
        String message,
        LocalDateTime createdAt
){

    public static ChatMessageResponse of(ChatMessage chatMessage, Long tradeId, Long senderId, String senderUsername) {

        return new ChatMessageResponse(
                chatMessage.getId(),
                tradeId,
                senderId,
                senderUsername,
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }
}