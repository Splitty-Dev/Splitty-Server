package com.chaegangjo.trade.repository;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.ChatMessage;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Slice;

public interface ChatMessageCustomRepository {

    Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long tradeId);
    Map<Long, ChatMessage> findLastMessagesByTradeIds(List<Long> tradeIds);
}
