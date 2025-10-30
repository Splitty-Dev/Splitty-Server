package com.chaegangjo.trade.application;

import com.chaegangjo.trade.presentation.IsJoinedTradeResponse;
import com.chaegangjo.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IsJoinTradeUsecase {

    private final ChatMemberService chatMemberService;

    public IsJoinedTradeResponse execute(Long memberId, Long goodsId) {
        return new IsJoinedTradeResponse(chatMemberService.existChatMember(goodsId, memberId));
    }
}
