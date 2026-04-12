package com.chaegangjo.purchase.repository;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PurchaseRequestChatMessageCustomRepository {

    Slice<PurchaseRequestChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long chatRoomId);

    List<PurchaseRequestChatMessage> findLastMessagesByChatRoomIds(List<Long> chatRoomIds);
}
