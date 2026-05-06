package com.chaegangjo.group.service;

import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.GroupMember;
import com.chaegangjo.group.domain.GroupRole;
import com.chaegangjo.group.repository.GroupMemberRepository;
import com.chaegangjo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.ALREADY_JOINED_GROUP;
import static com.chaegangjo.exception.errorcode.GroupErrorCode.GROUP_FULL;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public GroupMember saveGroupMember(Group group, Member member, GroupRole role) {
        if (groupMemberRepository.existsByGroupIdAndMemberId(group.getId(), member.getId())) {
            throw new GroupException(ALREADY_JOINED_GROUP);
        }
        if (group.isFull()) {
            throw new GroupException(GROUP_FULL);
        }
        GroupMember groupMember = groupMemberRepository.save(new GroupMember(group, member, role));
        group.incrementMemberCount();
        return groupMember;
    }

    public List<Group> findGroupsByMemberId(Long memberId) {
        return groupMemberRepository.findGroupsByMemberId(memberId);
    }

    public int countMembersByGroupId(Long groupId) {
        return groupMemberRepository.countByGroupId(groupId);
    }

    public boolean isMember(Long groupId, Long memberId) {
        return groupMemberRepository.existsByGroupIdAndMemberId(groupId, memberId);
    }

    public Map<Long, Integer> countMembersByGroupIds(List<Long> groupIds) {
        return groupMemberRepository.countMembersByGroupIds(groupIds);
    }
}
