package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.QGroup;
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
public class GroupCustomRepositoryImpl implements GroupCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Group> findAllByCursor(CursorPage page) {
        QGroup group = QGroup.group;

        List<Group> groups = queryFactory.selectFrom(group)
                .where(cursorId(page.getCursorId(), group))
                .orderBy(group.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = groups.size() > page.getSize();
        if (hasNext) groups.removeLast();

        return new SliceImpl<>(groups, PageRequest.of(0, page.getSize()), hasNext);
    }

    private BooleanExpression cursorId(Long cursorId, QGroup group) {
        return cursorId != null ? group.id.lt(cursorId) : null;
    }
}
