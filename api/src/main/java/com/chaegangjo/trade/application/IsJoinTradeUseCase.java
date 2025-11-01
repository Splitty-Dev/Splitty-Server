package com.chaegangjo.trade.application;

import com.chaegangjo.trade.dto.IsJoinedTradeResponse;
import com.chaegangjo.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IsJoinTradeUseCase {

    private final ChatMemberService chatMemberService;

    public IsJoinedTradeResponse execute(Long memberId, Long goodsId) {
        return new IsJoinedTradeResponse(chatMemberService.existActiveChatMember(goodsId, memberId));
    }
}
