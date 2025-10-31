package com.chaegangjo.chat.repository;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.chat.domain.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Slice;

public interface ChatMessageCustomRepository {

    Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long goodsId);
    List<ChatMessage> findLastChatMessagesByGoodsIds(List<Long> goodsIds);
}
