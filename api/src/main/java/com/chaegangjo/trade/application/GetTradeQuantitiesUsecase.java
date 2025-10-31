package com.chaegangjo.trade.application;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.trade.dto.GetTradeQuantitiesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetTradeQuantitiesUseCase {

    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;

    public GetTradeQuantitiesResponse execute(Long goodsId) {
        Goods goods = goodsService.findGoodsById(goodsId);
        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(goodsId);
        return GetTradeQuantitiesResponse.of(goods, chatMembers);
    }
}
