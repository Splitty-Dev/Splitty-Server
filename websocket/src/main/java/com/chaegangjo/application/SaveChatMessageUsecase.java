package com.chaegangjo.application;

import com.chaegangjo.dto.StompChatMessageResponse;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.fcm.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveChatMessageUsecase {

    private final ChatMessageService chatMessageService;
    private final ChatMemberService chatMemberService;
    private final FcmService fcmService;

    public StompChatMessageResponse execute(Long senderId, Long goodsId, String message) {
        ChatMember chatMember = chatMemberService.findChatMemberByGoodsIdAndMemberId(goodsId, senderId);
        ChatMessage chatMessage = chatMessageService.saveChatMessage(chatMember, message);

        //채팅 알림 전송
        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(goodsId);
        List<Long> memberIds = chatMembers.stream()
                .filter(cm -> !cm.getMember().getId().equals(senderId))
                .map(cm -> cm.getMember().getId()).toList();
        fcmService.sendChatMessages(memberIds, chatMember.getUsername(), message, goodsId);

        return StompChatMessageResponse.of(chatMessage, goodsId, senderId, chatMember.getUsername());
    }
}
