package com.chaegangjo.trade.repository;

import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.ChatMessage;
import org.springframework.data.domain.Slice;

public interface ChatMessageCustomRepository {

    Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long tradeId);
}
