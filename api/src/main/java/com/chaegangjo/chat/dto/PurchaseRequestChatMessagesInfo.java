package com.chaegangjo.chat.dto;

import com.chaegangjo.purchase.dto.response.PurchaseRequestInfo;

import java.util.List;

public record PurchaseRequestChatMessagesInfo(
        PurchaseRequestInfo purchaseRequest,
        ChatMemberInfo partner,
        List<PurchaseRequestChatMessageInfo> messages
) {
}
