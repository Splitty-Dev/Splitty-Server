package com.chaegangjo.trade.application;

import com.chaegangjo.trade.presentation.IsJoinedTradeResponse;
import com.chaegangjo.trade.service.TradeMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IsJoinTradeUsecase {

    private final TradeMemberService tradeMemberService;

    public IsJoinedTradeResponse execute(Long memberId, Long goodsId) {
        return new IsJoinedTradeResponse(tradeMemberService.existTradeMember(goodsId, memberId));
    }
}
