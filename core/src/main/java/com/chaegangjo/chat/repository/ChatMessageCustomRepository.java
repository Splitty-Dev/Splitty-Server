package com.chaegangjo.chat.repository;

import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatMessageCustomRepository {

    Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long goodsId);
    Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, List<Long> chatMemberIds);
    Slice<ChatMessage> findAllByOffset(int size, long offset, Long goodsId);
    List<ChatMessage> findLastChatMessagesByGoodsIds(List<Long> goodsIds);
}
