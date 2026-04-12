package com.chaegangjo.purchase.service;

import com.chaegangjo.exception.PurchaseRequestException;
import com.chaegangjo.exception.errorcode.PurchaseRequestErrorCode;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import com.chaegangjo.purchase.repository.PurchaseRequestChatMessageRepository;
import com.chaegangjo.purchase.repository.PurchaseRequestChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PurchaseRequestChatService {

    private final PurchaseRequestChatRoomRepository chatRoomRepository;
    private final PurchaseRequestChatMessageRepository chatMessageRepository;

    @Transactional
    public PurchaseRequestChatRoom findOrCreateChatRoom(PurchaseRequest purchaseRequest, Member helper) {
        return chatRoomRepository.findByPurchaseRequestIdAndHelperId(purchaseRequest.getId(), helper.getId())
                .orElseGet(() -> chatRoomRepository.save(new PurchaseRequestChatRoom(purchaseRequest, helper)));
    }

    public PurchaseRequestChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findByIdWithDetails(chatRoomId)
                .orElseThrow(() -> new PurchaseRequestException(PurchaseRequestErrorCode.PURCHASE_REQUEST_CHAT_ROOM_NOT_FOUND));
    }

    public List<PurchaseRequestChatRoom> findAllChatRoomsByMemberId(Long memberId) {
        return chatRoomRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public PurchaseRequestChatMessage saveMessage(PurchaseRequestChatRoom chatRoom, Member sender, String message) {
        return chatMessageRepository.save(new PurchaseRequestChatMessage(chatRoom, sender, message));
    }

    public Slice<PurchaseRequestChatMessage> findMessagesByCursor(Long chatRoomId, IdCreatedAtCursorPage cursorPage) {
        return chatMessageRepository.findAllByCursor(cursorPage, chatRoomId);
    }

    public List<PurchaseRequestChatMessage> getLastMessages(List<Long> chatRoomIds) {
        if (chatRoomIds.isEmpty()) return List.of();
        return chatMessageRepository.findLastMessagesByChatRoomIds(chatRoomIds);
    }
}
