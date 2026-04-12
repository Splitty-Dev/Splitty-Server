package com.chaegangjo.purchase.repository;

import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequestChatMessageRepository extends JpaRepository<PurchaseRequestChatMessage, Long>,
        PurchaseRequestChatMessageCustomRepository {
}
