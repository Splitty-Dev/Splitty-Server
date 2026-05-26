package com.chaegangjo.trade.application;

import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.trade.dto.ChangeTradeStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChangeTradeStatusUseCase {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;

    @Transactional
    public void execute(Long memberId, ChangeTradeStatusRequest request) {
        Member member = memberService.findMemberById(memberId);
        Goods goods = goodsService.findGoodsById(request.goodsId());
        chatMemberService.findChatMemberByGoodsAndMember(goods, member);
        goods.changeTradeStatus(request.tradeStatus());
    }
}
