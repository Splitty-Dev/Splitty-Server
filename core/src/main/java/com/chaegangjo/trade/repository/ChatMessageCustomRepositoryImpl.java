package com.chaegangjo.trade.repository;

import static com.chaegangjo.trade.domain.QChatMessage.chatMessage;

import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.MessageType;
import com.chaegangjo.trade.domain.QChatMessage;
import com.chaegangjo.trade.domain.QTrade;
import com.chaegangjo.trade.domain.QTradeMember;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

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

    @Override
    public Map<Long, ChatMessage> findLastMessagesByTradeIds(List<Long> tradeIds) {
        QGoods goods = QGoods.goods;
        QTradeMember tradeMember = QTradeMember.tradeMember;
        QTrade trade = QTrade.trade;
        List<Tuple> lastIds = queryFactory
                .select(chatMessage.tradeMember.trade.id, chatMessage.id.max())
                .from(chatMessage)
                .where(chatMessage.tradeMember.trade.id.in(tradeIds),
                        chatMessage.type.eq(MessageType.TEXT))
                .groupBy(chatMessage.tradeMember.trade.id)
                .fetch();

        List<Long> lastMessageIds = lastIds.stream()
                .map(tuple -> tuple.get(chatMessage.id.max()))
                .toList();

        List<ChatMessage> messages = queryFactory
                .selectFrom(chatMessage)
                .where(chatMessage.id.in(lastMessageIds))
                .fetch();

        return messages.stream()
                .collect(Collectors.toMap(
                        m -> m.getTradeMember().getTrade().getId(),
                        m -> m
                ));
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
