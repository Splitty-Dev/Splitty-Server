package com.chaegangjo.trade.application;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.exception.ChatMemberException;
import com.chaegangjo.exception.TradeException;
import com.chaegangjo.exception.errorcode.ChatMemberErrorCode;
import com.chaegangjo.exception.errorcode.TradeErrorCode;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.trade.dto.ConfirmTradeQuantityRequest;
import com.chaegangjo.trade.dto.ConfirmTradeQuantityRequest.ConfirmTradeQuantityInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ConfirmTradeQuantitiesUseCase {

    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;

    @Transactional
    public void execute(Long memberId, ConfirmTradeQuantityRequest request) {
        //TODO: 권한 확인
        Goods goods = goodsService.findGoodsById(request.goodsId());
        validateTotalQuantity(request, goods);

        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(request.goodsId());
        changeQuantities(request, chatMembers);

        goods.closeTrade();
    }

    private static void changeQuantities(ConfirmTradeQuantityRequest request, List<ChatMember> chatMembers) {
        chatMembers
                .forEach(
                        chatMember -> {
                            int confirmedQuantity = request.quantities().stream()
                                    .filter(q -> q.getMemberId().equals(chatMember.getMember().getId()))
                                    .findFirst()
                                    .orElseThrow(() -> new ChatMemberException(ChatMemberErrorCode.TRADE_MEMBER_NOT_FOUND))
                                    .getQuantity();
                            chatMember.changeQuantity(confirmedQuantity);
                        }
                );
    }

    private static void validateTotalQuantity(ConfirmTradeQuantityRequest request, Goods goods) {
        int totalQuantity  = request.quantities().stream()
                .mapToInt(ConfirmTradeQuantityInfo::getQuantity)
                .sum();
        if (goods.getTotalQuantity() != totalQuantity) {
            throw new TradeException(TradeErrorCode.TOTAL_QUANTITY_MISMATCH);
        }
    }
}
