package com.chaegangjo.trade.service;

import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessage saveChatMessage(TradeMember tradeMember, String message) {

        ChatMessage chatMessage = new ChatMessage(tradeMember, message);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(Long tradeId, Long cursorId) {
        return null;
    }
}
