package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.Group;

import java.util.List;
import java.util.Map;

public interface GroupMemberCustomRepository {

    List<Group> findGroupsByMemberId(Long memberId);

    Map<Long, Integer> countMembersByGroupIds(List<Long> groupIds);
}
