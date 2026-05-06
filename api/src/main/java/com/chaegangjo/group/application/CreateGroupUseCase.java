package com.chaegangjo.group.application;

import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.GroupChatRoom;
import com.chaegangjo.group.domain.GroupRole;
import com.chaegangjo.group.dto.GroupInfo;
import com.chaegangjo.group.dto.request.CreateGroupRequest;
import com.chaegangjo.group.service.GroupChatMemberService;
import com.chaegangjo.group.service.GroupChatRoomService;
import com.chaegangjo.group.service.GroupMemberService;
import com.chaegangjo.group.service.GroupService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CreateGroupUseCase {

    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final GroupChatRoomService groupChatRoomService;
    private final GroupChatMemberService groupChatMemberService;
    private final MemberService memberService;

    @Transactional
    public GroupInfo execute(CreateGroupRequest request, Long memberId) {
        Member host = memberService.findMemberById(memberId);
        String joinCode = generateJoinCode();

        Group group = groupService.saveGroup(
                Group.builder()
                        .host(host)
                        .name(request.name())
                        .description(request.description())
                        .joinCode(joinCode)
                        .maxMemberCount(request.maxMemberCount())
                        .build()
        );

        groupMemberService.saveGroupMember(group, host, GroupRole.HOST);

        GroupChatRoom chatRoom = groupChatRoomService.createChatRoom(group);
        groupChatMemberService.saveGroupChatMember(chatRoom, host);

        return GroupInfo.from(group);
    }

    private String generateJoinCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
    }
}
