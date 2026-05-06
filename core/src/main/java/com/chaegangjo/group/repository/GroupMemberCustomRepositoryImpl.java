package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.QGroup;
import com.chaegangjo.group.domain.QGroupMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class GroupMemberCustomRepositoryImpl implements GroupMemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Group> findGroupsByMemberId(Long memberId) {
        QGroupMember groupMember = QGroupMember.groupMember;
        QGroup group = QGroup.group;

        return queryFactory.select(groupMember.group)
                .from(groupMember)
                .join(groupMember.group, group)
                .where(groupMember.member.id.eq(memberId))
                .orderBy(group.id.desc())
                .fetch();
    }

    @Override
    public Map<Long, Integer> countMembersByGroupIds(List<Long> groupIds) {
        QGroupMember groupMember = QGroupMember.groupMember;

        List<Tuple> result = queryFactory
                .select(groupMember.group.id, groupMember.count())
                .from(groupMember)
                .where(groupMember.group.id.in(groupIds))
                .groupBy(groupMember.group.id)
                .fetch();

        return result.stream().collect(Collectors.toMap(
                t -> t.get(groupMember.group.id),
                t -> t.get(groupMember.count()).intValue()
        ));
    }
}
