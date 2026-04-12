package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.StartPurchaseRequestChatResponse;
import com.chaegangjo.exception.PurchaseRequestException;
import com.chaegangjo.exception.errorcode.PurchaseRequestErrorCode;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import com.chaegangjo.purchase.service.PurchaseRequestChatService;
import com.chaegangjo.purchase.service.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class StartPurchaseRequestChatUseCase {

    private final MemberService memberService;
    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseRequestChatService purchaseRequestChatService;

    @Transactional
    public StartPurchaseRequestChatResponse execute(Long purchaseRequestId, Long helperId) {
        PurchaseRequest purchaseRequest = purchaseRequestService.findById(purchaseRequestId);

        if (purchaseRequest.getRequester().getId().equals(helperId)) {
            throw new PurchaseRequestException(PurchaseRequestErrorCode.CANNOT_CHAT_WITH_SELF);
        }

        Member helper = memberService.findMemberById(helperId);
        PurchaseRequestChatRoom chatRoom = purchaseRequestChatService.findOrCreateChatRoom(purchaseRequest, helper);

        return StartPurchaseRequestChatResponse.from(chatRoom);
    }
}
