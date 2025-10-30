package com.chaegangjo.chat.service;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.domain.MessageType;
import com.chaegangjo.chat.repository.ChatMessageRepository;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
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
    public ChatMessage saveChatMessage(ChatMember chatMember, String message) {
        ChatMessage chatMessage = new ChatMessage(chatMember, message);
        return chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public ChatMessage saveChatMessage(ChatMember chatMember, MessageType messageType) {
        ChatMessage chatMessage = new ChatMessage(chatMember, messageType);
        return chatMessageRepository.save(chatMessage);
    }

    public Slice<ChatMessage> findAllByGoodsIdAndCursor(Long goodsId, IdCreatedAtCursorPage cursorPage) {
        return chatMessageRepository.findAllByCursor(cursorPage, goodsId);
    }

    public List<ChatMessage> getLastChatMessages(List<Long> goodsIds) {
        return chatMessageRepository.findLastChatMessagesByGoodsIds(goodsIds);
    }
}
