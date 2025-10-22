package com.chaegangjo.trade.repository;

import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.service.ChatMessageService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageCustomRepository {
}
