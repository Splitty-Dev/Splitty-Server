package com.chaegangjo.purchase.repository;

import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequestChatRoomCustomRepository {

    List<PurchaseRequestChatRoom> findAllByMemberId(Long memberId);

    Optional<PurchaseRequestChatRoom> findByIdWithDetails(Long chatRoomId);

    Optional<PurchaseRequestChatRoom> findByPurchaseRequestIdAndHelperId(Long purchaseRequestId, Long helperId);
}
