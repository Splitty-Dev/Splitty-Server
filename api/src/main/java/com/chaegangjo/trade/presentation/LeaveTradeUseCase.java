package com.chaegangjo.trade.presentation;

import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LeaveTradeUseCase {

    private final ChatMemberService chatMemberService;
    private final GoodsService goodsService;

    public void execute(Long memberId, Long goodsId) {
        Goods goods = goodsService.findGoodsById(goodsId);
        goodsService.decrementCurrentParticipant(goods);
        chatMemberService.deactiveChatMember(memberId, goodsId);
    }
}
