package com.chaegangjo.trade.repository;

import com.chaegangjo.trade.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageCustomRepository {

//    Optional<ChatMessage> findTopByTradeMember_TradeAndTypeOrderByCreatedAtDesc(Trade trade, MessageType type);
}
