package com.chaegangjo.group.application;

import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.GroupChatRoom;
import com.chaegangjo.group.domain.GroupRole;
import com.chaegangjo.group.dto.GroupInfo;
import com.chaegangjo.group.dto.request.JoinGroupRequest;
import com.chaegangjo.group.service.GroupChatMemberService;
import com.chaegangjo.group.service.GroupChatRoomService;
import com.chaegangjo.group.service.GroupMemberService;
import com.chaegangjo.group.service.GroupService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class JoinGroupUseCase {

    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final GroupChatRoomService groupChatRoomService;
    private final GroupChatMemberService groupChatMemberService;
    private final MemberService memberService;

    @Transactional
    public GroupInfo execute(JoinGroupRequest request, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Group group = groupService.findGroupByJoinCode(request.joinCode());
        groupMemberService.saveGroupMember(group, member, GroupRole.MEMBER);

        GroupChatRoom chatRoom = groupChatRoomService.findByGroupId(group.getId());
        groupChatMemberService.saveGroupChatMember(chatRoom, member);

        return GroupInfo.from(group);
    }
}
