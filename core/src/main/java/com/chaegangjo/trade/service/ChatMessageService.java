package com.chaegangjo.trade.service;

import static com.chaegangjo.paging.PageProperties.CHAT_MESSAGE_PAGE_SIZE;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.MessageType;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.repository.ChatMessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ChatMessage saveChatMessage(TradeMember tradeMember, MessageType messageType) {
        ChatMessage chatMessage = new ChatMessage(tradeMember, messageType);
        return chatMessageRepository.save(chatMessage);
    }

    public Slice<ChatMessage> getChatMessages(Long goodsId, Long cursorId, LocalDateTime createdAt) {
        return chatMessageRepository.findAllByCursor(
                new IdCreatedAtCursorPage(CHAT_MESSAGE_PAGE_SIZE, cursorId, createdAt), goodsId);
    }

    public List<ChatMessage> getLastMessages(List<Long> goodsIds) {
        return chatMessageRepository.findLastMessagesByGoodsIds(goodsIds);
    }
}
