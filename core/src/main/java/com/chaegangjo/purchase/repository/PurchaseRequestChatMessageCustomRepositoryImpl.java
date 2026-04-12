package com.chaegangjo.purchase.repository;

import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import com.chaegangjo.purchase.domain.QPurchaseRequestChatMessage;
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
public class PurchaseRequestChatMessageCustomRepositoryImpl implements PurchaseRequestChatMessageCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PurchaseRequestChatMessage> findAllByCursor(IdCreatedAtCursorPage page, Long chatRoomId) {
        QPurchaseRequestChatMessage chatMessage = QPurchaseRequestChatMessage.purchaseRequestChatMessage;
        QMember sender = QMember.member;

        List<PurchaseRequestChatMessage> messages = queryFactory.selectFrom(chatMessage)
                .join(chatMessage.sender, sender).fetchJoin()
                .where(
                        chatMessage.chatRoom.id.eq(chatRoomId),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), chatMessage)
                )
                .orderBy(chatMessage.createdAt.desc(), chatMessage.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = messages.size() > page.getSize();
        if (hasNext) messages.removeLast();

        return new SliceImpl<>(messages, PageRequest.of(0, page.getSize()), hasNext);
    }

    @Override
    public List<PurchaseRequestChatMessage> findLastMessagesByChatRoomIds(List<Long> chatRoomIds) {
        QPurchaseRequestChatMessage chatMessage = QPurchaseRequestChatMessage.purchaseRequestChatMessage;
        QMember sender = QMember.member;

        List<Long> lastIds = queryFactory
                .select(chatMessage.id.max())
                .from(chatMessage)
                .where(chatMessage.chatRoom.id.in(chatRoomIds))
                .groupBy(chatMessage.chatRoom.id)
                .fetch();

        return queryFactory.selectFrom(chatMessage)
                .join(chatMessage.sender, sender).fetchJoin()
                .where(chatMessage.id.in(lastIds))
                .fetch();
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId,
                                                    QPurchaseRequestChatMessage chatMessage) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return chatMessage.createdAt.lt(cursorCreatedAt)
                .or(chatMessage.createdAt.eq(cursorCreatedAt)
                        .and(chatMessage.id.lt(cursorId)));
    }
}
