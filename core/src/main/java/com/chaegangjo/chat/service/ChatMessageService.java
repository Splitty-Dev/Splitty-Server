package com.chaegangjo.chat.service;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.enums.MessageType;
import com.chaegangjo.chat.repository.ChatMessageRepository;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Slice<ChatMessage> findAllByGoodsIdAndCursor(List<Long> chatMemberIds, IdCreatedAtCursorPage cursorPage) {
        return chatMessageRepository.findAllByCursor(cursorPage, chatMemberIds);
    }

    public Slice<ChatMessage> findAllByGoodsIdAndOffset(Long goodsId, int size, long offset) {
        return chatMessageRepository.findAllByOffset(size, offset, goodsId);
    }

    public List<ChatMessage> getLastChatMessages(List<Long> goodsIds) {
        return chatMessageRepository.findLastChatMessagesByGoodsIds(goodsIds);
    }
}
