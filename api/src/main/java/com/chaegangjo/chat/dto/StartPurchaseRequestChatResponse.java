package com.chaegangjo.chat.dto;

import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import io.swagger.v3.oas.annotations.media.Schema;

public record StartPurchaseRequestChatResponse(
        @Schema(example = "10")
        Long chatRoomId,
        @Schema(example = "5")
        Long purchaseRequestId,
        @Schema(example = "아이폰 케이블 대신 사다 주실 분 구해요")
        String purchaseRequestTitle
) {

    public static StartPurchaseRequestChatResponse from(PurchaseRequestChatRoom chatRoom) {
        return new StartPurchaseRequestChatResponse(
                chatRoom.getId(),
                chatRoom.getPurchaseRequest().getId(),
                chatRoom.getPurchaseRequest().getTitle()
        );
    }
}
