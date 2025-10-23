package com.chaegangjo.trade.application;

import com.chaegangjo.exception.TradeException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.trade.domain.MessageType;
import com.chaegangjo.trade.domain.Trade;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.dto.request.JoinTradeRequest;
import com.chaegangjo.trade.service.ChatMessageService;
import com.chaegangjo.trade.service.TradeMemberService;
import com.chaegangjo.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.errorcode.TradeErrorCode.*;

@RequiredArgsConstructor
@Component
public class JoinTradeUsecase {

    private final GoodsService goodsService;
    private final TradeService tradeService;
    private final TradeMemberService tradeMemberService;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;

    @Transactional
    public void execute(JoinTradeRequest request, Long buyerId) {
        Goods goods = goodsService.findGoodsById(request.goodsId());
        if (!goods.isOpend()) {
            throw new TradeException(TRADE_NOT_OPENED);
        }
        if (goods.canBuy(request.quantity())) {
            goods.joinTrade(request.quantity());
        } else {
            throw new TradeException(INSUFFICIENT_STOCK);
        }

        Trade trade = tradeService.findTradeByGoods(goods);
        Member buyer = memberService.findMemberById(buyerId);
        if (tradeMemberService.existsByTradeAndMember(trade, buyer)) {
            throw new TradeException(ALREADY_JOINED);
        }
        TradeMember tradeMember = tradeMemberService.saveTradeMember(trade, buyer, request.quantity());

        chatMessageService.saveChatMessage(tradeMember, MessageType.ENTER); //채팅방 입장 메시지 저장
    }
}
