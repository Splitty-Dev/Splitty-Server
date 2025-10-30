package com.chaegangjo.application;

import com.chaegangjo.dto.StompChatMessageResponse;
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

    public StompChatMessageResponse execute(Long senderId, Long goodsId, String message) {
        TradeMember tradeMember = tradeMemberService.findTradeMember(goodsId, senderId);
        ChatMessage chatMessage = chatMessageService.saveChatMessage(tradeMember, message);

        return StompChatMessageResponse.of(chatMessage, goodsId, senderId, tradeMember.getUsername());
    }
}
