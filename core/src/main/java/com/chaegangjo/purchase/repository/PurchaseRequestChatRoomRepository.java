package com.chaegangjo.purchase.repository;

import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequestChatRoomRepository extends JpaRepository<PurchaseRequestChatRoom, Long>,
        PurchaseRequestChatRoomCustomRepository {
}
