package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.ChatMessageResponse;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.service.ChatMessageService;
import com.chaegangjo.trade.service.TradeMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class SaveChatMessageUsecase {

    private final ChatMessageService chatMessageService;
    private final TradeMemberService tradeMemberService;

    public ChatMessageResponse execute(Long senderId, Long tradeId, String message) {
        TradeMember tradeMember = tradeMemberService.findTradeMember(senderId, tradeId);
        ChatMessage chatMessage = chatMessageService.saveChatMessage(tradeMember, message);

        return ChatMessageResponse.of(chatMessage, tradeId, senderId, tradeMember.getUsername());
    }
}
