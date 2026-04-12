package com.chaegangjo.chat.dto;

import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PurchaseRequestChatMessageInfo(
        @Schema(example = "1")
        Long id,
        @Schema(example = "3")
        Long senderId,
        @Schema(example = "안녕하세요, 도와드릴게요!")
        String message,
        @Schema(example = "2026-04-07T10:00:00")
        LocalDateTime createdAt
) {

    public static PurchaseRequestChatMessageInfo from(PurchaseRequestChatMessage message) {
        return new PurchaseRequestChatMessageInfo(
                message.getId(),
                message.getSender().getId(),
                message.getMessage(),
                message.getCreatedAt()
        );
    }
}
