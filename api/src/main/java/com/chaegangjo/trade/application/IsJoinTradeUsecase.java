package com.chaegangjo.trade.application;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.trade.presentation.IsJoinedTradeResponse;
import com.chaegangjo.trade.service.TradeMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IsJoinTradeUsecase {

    private final GoodsService goodsService;
    private final TradeMemberService tradeMemberService;

    public IsJoinedTradeResponse execute(Long memberId, Long goodsId) {
        Goods goods = goodsService.findGoodsById(goodsId);
        if (memberId.equals(goods.getSeller().getId()) || tradeMemberService.existTradeMember(goodsId, memberId)) {
            return new IsJoinedTradeResponse(true);
        }
        return new IsJoinedTradeResponse(false);
    }
}
