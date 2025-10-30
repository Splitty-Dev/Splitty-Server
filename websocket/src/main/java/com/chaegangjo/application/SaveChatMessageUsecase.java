package com.chaegangjo.application;

import com.chaegangjo.dto.StompChatMessageResponse;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveChatMessageUsecase {

    private final ChatMessageService chatMessageService;
    private final ChatMemberService chatMemberService;

    public StompChatMessageResponse execute(Long senderId, Long goodsId, String message) {
        ChatMember chatMember = chatMemberService.findChatMember(goodsId, senderId);
        ChatMessage chatMessage = chatMessageService.saveChatMessage(chatMember, message);

        return StompChatMessageResponse.of(chatMessage, goodsId, senderId, chatMember.getUsername());
    }
}
