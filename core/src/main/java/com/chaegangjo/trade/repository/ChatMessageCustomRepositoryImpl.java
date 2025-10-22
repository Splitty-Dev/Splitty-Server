package com.chaegangjo.trade.repository;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatMessageCustomRepositoryImpl implements ChatMessageCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long tradeId) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        QTradeMember tradeMember = QTradeMember.tradeMember;

        List<ChatMessage> chatMessages = queryFactory.selectFrom(chatMessage)
                .join(chatMessage.tradeMember, tradeMember).fetchJoin()
                .where(
                        eqTradeId(tradeId, chatMessage),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), chatMessage)
                )
                .orderBy(chatMessage.createdAt.desc(), chatMessage.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = chatMessages.size() > page.getSize();
        if (hasNext) chatMessages.removeLast();

        return new SliceImpl<>(chatMessages, PageRequest.of(0, page.getSize()), hasNext);
    }

    private BooleanExpression eqTradeId(Long tradeId, QChatMessage chatMessage) {
        if (tradeId == null) return null;
        return chatMessage.tradeMember.trade.id.eq(tradeId);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QChatMessage chatMessage) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return chatMessage.createdAt.lt(cursorCreatedAt)
                .or(chatMessage.createdAt.eq(cursorCreatedAt)
                        .and(chatMessage.id.lt(cursorId)));
    }
}
