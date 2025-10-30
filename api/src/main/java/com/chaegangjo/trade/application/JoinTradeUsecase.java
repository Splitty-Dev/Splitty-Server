package com.chaegangjo.trade.application;

import static com.chaegangjo.exception.errorcode.TradeErrorCode.ALREADY_JOINED;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.INSUFFICIENT_STOCK;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.TRADE_NOT_OPENED;

import com.chaegangjo.exception.TradeException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.MessageType;
import com.chaegangjo.trade.dto.JoinTradeRequest;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class JoinTradeUseCase {

    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;

    @Transactional
    public void execute(JoinTradeRequest request, Long buyerId) {
        Goods goods = goodsService.findGoodsById(request.goodsId());
        if (!goods.isOpened()) {
            throw new TradeException(TRADE_NOT_OPENED);
        }
        if (goods.canBuy(request.quantity())) {
            goods.joinTrade(request.quantity());
        } else {
            throw new TradeException(INSUFFICIENT_STOCK);
        }

        Member buyer = memberService.findMemberById(buyerId);
        if (chatMemberService.existsChatMemberByGoodsAndMember(goods, buyer)) {
            throw new TradeException(ALREADY_JOINED);
        }
        ChatMember chatMember = chatMemberService.saveChatMember(goods, buyer, request.quantity());

        chatMessageService.saveChatMessage(chatMember, MessageType.ENTER); //채팅방 입장 메시지 저장
    }
}
