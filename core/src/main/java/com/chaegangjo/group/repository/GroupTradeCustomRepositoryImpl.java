package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupTrade;
import com.chaegangjo.group.domain.QGroupTrade;
import com.chaegangjo.paging.CursorPage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GroupTradeCustomRepositoryImpl implements GroupTradeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GroupTrade> findAllByGroupIdAndCursor(Long groupId, CursorPage page) {
        QGroupTrade groupTrade = QGroupTrade.groupTrade;

        List<GroupTrade> trades = queryFactory.selectFrom(groupTrade)
                .where(
                        groupTrade.group.id.eq(groupId),
                        cursorId(page.getCursorId(), groupTrade)
                )
                .orderBy(groupTrade.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = trades.size() > page.getSize();
        if (hasNext) trades.removeLast();

        return new SliceImpl<>(trades, PageRequest.of(0, page.getSize()), hasNext);
    }

    private BooleanExpression cursorId(Long cursorId, QGroupTrade groupTrade) {
        return cursorId != null ? groupTrade.id.lt(cursorId) : null;
    }
}
