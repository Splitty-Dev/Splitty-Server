package com.chaegangjo.trade.application;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.enums.MessageType;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.exception.TradeException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.logger.UserAction;
import com.chaegangjo.logger.UserActionLogger;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.trade.dto.JoinTradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.chat.enums.TradeRole.BUYER;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.INSUFFICIENT_STOCK;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.TRADE_NOT_OPENED;

@RequiredArgsConstructor
@Component
public class JoinTradeUseCase {

    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;
    private final UserActionLogger userActionLogger;

    @Transactional
    public void execute(JoinTradeRequest request, Long buyerId) {
        Member buyer = memberService.findMemberById(buyerId);
        Goods goods = goodsService.findGoodsByIdForUpdate(request.goodsId());
        if (!goods.isOpened()) {
            throw new TradeException(TRADE_NOT_OPENED);
        }
        if (goods.canBuy(request.quantity())) {
            goods.joinTrade(request.quantity());
        } else {
            throw new TradeException(INSUFFICIENT_STOCK);
        }
        if (chatMemberService.existsChatMemberByGoodsAndMember(goods, buyer)) {
            chatMemberService.findChatMemberByGoodsAndMember(goods, buyer).activate();
        } else {
            ChatMember chatMember = chatMemberService.saveChatMember(goods, buyer, request.quantity(), BUYER);
            chatMessageService.saveChatMessage(chatMember, MessageType.ENTER);
        }

        userActionLogger.logAction(buyerId, UserAction.ENTER, request.goodsId(), goods.getCategory().getId(), goods.getUnitPrice());
    }
}
