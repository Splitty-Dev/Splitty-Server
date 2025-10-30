package com.chaegangjo.trade.repository;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Slice;

public interface ChatMessageCustomRepository {

    Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long goodsId);
    List<ChatMessage> findLastMessagesByGoodsIds(List<Long> goodsIds);
}
