package com.chaegangjo.application;

import com.chaegangjo.dto.StompGroupChatMessageResponse;
import com.chaegangjo.fcm.FcmService;
import com.chaegangjo.group.domain.GroupChatMember;
import com.chaegangjo.group.domain.GroupChatMessage;
import com.chaegangjo.group.service.GroupChatMemberService;
import com.chaegangjo.group.service.GroupChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SaveGroupChatMessageUsecase {

    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatMessageService groupChatMessageService;
    private final FcmService fcmService;

    public StompGroupChatMessageResponse execute(Long senderId, Long chatRoomId, String message) {
        GroupChatMember sender = groupChatMemberService.findByChatRoomIdAndMemberId(chatRoomId, senderId);
        GroupChatMessage chatMessage = groupChatMessageService.saveMessage(sender, message);

        List<GroupChatMember> allMembers = groupChatMemberService.findAllByChatRoomId(chatRoomId);
        List<Long> otherMemberIds = allMembers.stream()
                .map(m -> m.getMember().getId())
                .filter(id -> !id.equals(senderId))
                .toList();
        fcmService.sendGroupChatMessage(otherMemberIds, sender.getUsername(), message, chatRoomId);

        return StompGroupChatMessageResponse.of(chatMessage, chatRoomId, senderId, sender.getUsername());
    }
}
