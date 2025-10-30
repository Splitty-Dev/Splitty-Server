package com.chaegangjo.trade.repository;

import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.MessageType;
import com.chaegangjo.trade.domain.QChatMessage;
import com.chaegangjo.trade.domain.QTradeMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
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
    public Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long goodsId) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        QTradeMember tradeMember = QTradeMember.tradeMember;

        List<ChatMessage> chatMessages = queryFactory.selectFrom(chatMessage)
                .join(chatMessage.tradeMember, tradeMember).fetchJoin()
                .where(
                        eqGoodsId(goodsId, chatMessage),
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
    public List<ChatMessage> findLastMessagesByGoodsIds(List<Long> goodsIds) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        QTradeMember tradeMember = QTradeMember.tradeMember;
        QGoods goods = QGoods.goods;

        List<Long> lastIds = queryFactory
                .select(chatMessage.id.max())
                .from(chatMessage)
                .where(chatMessage.tradeMember.goods.id.in(goodsIds),
                        chatMessage.type.eq(MessageType.TEXT))
                .groupBy(chatMessage.tradeMember.goods.id)
                .fetch();

        return queryFactory
                .selectFrom(chatMessage)
                .join(chatMessage.tradeMember, tradeMember).fetchJoin()
                .join(tradeMember.goods, goods).fetchJoin()
                .where(chatMessage.id.in(lastIds))
                .fetch();
    }

    private BooleanExpression eqGoodsId(Long goodsId, QChatMessage chatMessage) {
        if (goodsId == null) return null;
        return chatMessage.tradeMember.goods.id.eq(goodsId);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QChatMessage chatMessage) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return chatMessage.createdAt.lt(cursorCreatedAt)
                .or(chatMessage.createdAt.eq(cursorCreatedAt)
                        .and(chatMessage.id.lt(cursorId)));
    }
}
