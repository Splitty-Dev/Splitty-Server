package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupChatMessage;
import com.chaegangjo.group.domain.QGroupChatMessage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
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
public class GroupChatMessageCustomRepositoryImpl implements GroupChatMessageCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GroupChatMessage> findAllByCursor(List<Long> chatMemberIds, IdCreatedAtCursorPage page) {
        QGroupChatMessage groupChatMessage = QGroupChatMessage.groupChatMessage;

        List<GroupChatMessage> messages = queryFactory.selectFrom(groupChatMessage)
                .where(
                        groupChatMessage.groupChatMember.id.in(chatMemberIds),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), groupChatMessage)
                )
                .orderBy(groupChatMessage.createdAt.desc(), groupChatMessage.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = messages.size() > page.getSize();
        if (hasNext) messages.removeLast();

        return new SliceImpl<>(messages, PageRequest.of(0, page.getSize()), hasNext);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QGroupChatMessage msg) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return msg.createdAt.lt(cursorCreatedAt)
                .or(msg.createdAt.eq(cursorCreatedAt).and(msg.id.lt(cursorId)));
    }
}
