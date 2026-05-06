package com.chaegangjo.group.application;

import com.chaegangjo.group.dto.GroupInfo;
import com.chaegangjo.group.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetGroupListUseCase {

    private final GroupMemberService groupMemberService;

    public List<GroupInfo> execute(Long memberId) {
        return groupMemberService.findGroupsByMemberId(memberId).stream()
                .map(GroupInfo::from)
                .toList();
    }
}
