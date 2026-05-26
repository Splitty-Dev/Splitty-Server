package com.chaegangjo.application;

import com.chaegangjo.dto.StompGroupChatMessageResponse;
import com.chaegangjo.group.domain.GroupChatMember;
import com.chaegangjo.group.domain.GroupChatMessage;
import com.chaegangjo.group.service.GroupChatMemberService;
import com.chaegangjo.group.service.GroupChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveGroupChatMessageUsecase {

    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatMessageService groupChatMessageService;

    public StompGroupChatMessageResponse execute(Long senderId, Long chatRoomId, String message) {
        GroupChatMember sender = groupChatMemberService.findByChatRoomIdAndMemberId(chatRoomId, senderId);
        GroupChatMessage chatMessage = groupChatMessageService.saveMessage(sender, message);

        return StompGroupChatMessageResponse.of(chatMessage, chatRoomId, senderId, sender.getUsername());
    }
}
