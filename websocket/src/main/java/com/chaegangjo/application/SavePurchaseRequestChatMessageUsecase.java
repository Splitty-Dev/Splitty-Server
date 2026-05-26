package com.chaegangjo.application;

import com.chaegangjo.dto.StompPurchaseRequestChatMessageResponse;
import com.chaegangjo.exception.PurchaseRequestException;
import com.chaegangjo.exception.errorcode.PurchaseRequestErrorCode;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import com.chaegangjo.purchase.service.PurchaseRequestChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SavePurchaseRequestChatMessageUsecase {

    private final PurchaseRequestChatService purchaseRequestChatService;
    private final MemberService memberService;

    public StompPurchaseRequestChatMessageResponse execute(Long senderId, Long chatRoomId, String message) {
        PurchaseRequestChatRoom chatRoom = purchaseRequestChatService.findChatRoomById(chatRoomId);

        Long requesterId = chatRoom.getPurchaseRequest().getRequester().getId();
        Long helperId = chatRoom.getHelper().getId();
        if (!senderId.equals(requesterId) && !senderId.equals(helperId)) {
            throw new PurchaseRequestException(PurchaseRequestErrorCode.UNAUTHORIZED_CHAT_ROOM);
        }

        Member sender = memberService.findMemberById(senderId);
        PurchaseRequestChatMessage chatMessage = purchaseRequestChatService.saveMessage(chatRoom, sender, message);

        return StompPurchaseRequestChatMessageResponse.of(chatMessage, chatRoomId, senderId, sender.getUsername());
    }
}
