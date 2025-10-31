package com.chaegangjo.member.repository;


import com.chaegangjo.member.domain.NotificationHistory;
import com.chaegangjo.member.domain.QNotification;
import com.chaegangjo.member.domain.QNotificationHistory;
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
public class NotificationHistoryCustomRepositoryImpl implements NotificationHistoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<NotificationHistory> findAllByCursor(IdCreatedAtCursorPage page, Long memberId) {
        QNotificationHistory notificationHistory = QNotificationHistory.notificationHistory;
        QNotification notification = QNotification.notification;

        List<NotificationHistory> notificationHistories = queryFactory.selectFrom(notificationHistory)
                .join(notificationHistory.notification, notification)
                .where(
                        eqMemberId(memberId, notificationHistory),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), notificationHistory)
                )
                .orderBy(notification.createdAt.desc(), notificationHistory.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = notificationHistories.size() > page.getSize();
        if (hasNext) notificationHistories.removeLast();

        return new SliceImpl<>(notificationHistories, PageRequest.of(0, page.getSize()), hasNext);
    }


    private BooleanExpression eqMemberId(Long memberId, QNotificationHistory notificationHistory) {
        if (memberId == null) return null;
        return notificationHistory.member.id.eq(memberId);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QNotificationHistory notificationHistory) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return notificationHistory.notification.createdAt.lt(cursorCreatedAt)
                .or(notificationHistory.notification.createdAt.eq(cursorCreatedAt)
                        .and(notificationHistory.id.lt(cursorId)));
    }
}
