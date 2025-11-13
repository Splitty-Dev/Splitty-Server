package com.chaegangjo.chat.repository;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.domain.QChatMember;
import com.chaegangjo.chat.domain.QChatMessage;
import com.chaegangjo.chat.enums.MessageType;
import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
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
        QChatMember chatMember = QChatMember.chatMember;

        List<ChatMessage> chatMessages = queryFactory.selectFrom(chatMessage)
                .join(chatMessage.chatMember, chatMember).fetchJoin()
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
    public Slice<ChatMessage> findAllByCursor(IdCreatedAtCursorPage page, List<Long> chatMemberIds) {
        QChatMessage chatMessage = QChatMessage.chatMessage;

        List<ChatMessage> chatMessages = queryFactory.selectFrom(chatMessage)
                .where(chatMessage.chatMember.id.in(chatMemberIds),
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
    public List<ChatMessage> findLastChatMessagesByGoodsIds(List<Long> goodsIds) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        QChatMember chatMember = QChatMember.chatMember;
        QGoods goods = QGoods.goods;

        List<Long> lastIds = queryFactory
                .select(chatMessage.id.max())
                .from(chatMessage)
                .where(chatMessage.chatMember.goods.id.in(goodsIds),
                        chatMessage.type.eq(MessageType.TEXT))
                .groupBy(chatMessage.chatMember.goods.id)
                .fetch();

        return queryFactory
                .selectFrom(chatMessage)
                .join(chatMessage.chatMember, chatMember).fetchJoin()
                .join(chatMember.goods, goods).fetchJoin()
                .where(chatMessage.id.in(lastIds))
                .fetch();
    }

    private BooleanExpression eqGoodsId(Long goodsId, QChatMessage chatMessage) {
        if (goodsId == null) return null;
        return chatMessage.chatMember.goods.id.eq(goodsId);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QChatMessage chatMessage) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return chatMessage.createdAt.lt(cursorCreatedAt)
                .or(chatMessage.createdAt.eq(cursorCreatedAt)
                        .and(chatMessage.id.lt(cursorId)));
    }
}
