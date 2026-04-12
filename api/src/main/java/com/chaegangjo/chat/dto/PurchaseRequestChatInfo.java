package com.chaegangjo.chat.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PurchaseRequestChatInfo(
        @Schema(example = "1")
        Long chatRoomId,
        @Schema(example = "5")
        Long purchaseRequestId,
        @Schema(example = "아이폰 케이블 대신 사다 주실 분 구해요")
        String purchaseRequestTitle,
        @Schema(example = "2")
        Long partnerId,
        @Schema(example = "귀여운고양이77")
        String partnerUsername,
        @Schema(example = "https://profile-image.png")
        String partnerProfileImageUrl,
        @Schema(example = "안녕하세요, 도와드릴게요!")
        String lastMessage,
        @Schema(example = "2026-04-07T10:00:00")
        LocalDateTime updatedAt
) {

    public static PurchaseRequestChatInfo of(PurchaseRequestChatRoom chatRoom,
                                              PurchaseRequestChatMessage lastMessage,
                                              Long currentMemberId) {
        PurchaseRequest purchaseRequest = chatRoom.getPurchaseRequest();
        Member partner = chatRoom.getPurchaseRequest().getRequester().getId().equals(currentMemberId)
                ? chatRoom.getHelper()
                : chatRoom.getPurchaseRequest().getRequester();

        String lastMessageText = lastMessage != null ? lastMessage.getMessage() : null;
        LocalDateTime updatedAt = lastMessage != null ? lastMessage.getCreatedAt() : chatRoom.getCreatedAt();

        return new PurchaseRequestChatInfo(
                chatRoom.getId(),
                purchaseRequest.getId(),
                purchaseRequest.getTitle(),
                partner.getId(),
                partner.getUsername(),
                partner.getProfileImageUrl(),
                lastMessageText,
                updatedAt
        );
    }
}
