package com.chaegangjo.chat.dto;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.Trade;
import java.time.LocalDateTime;

public record ChatInfo(
        Long tradeId,
        Long goodsId,
        String name,
        String lastMessage,
        LocalDateTime updatedAt,
        int currParticipants,
        String imageName
) {

    public static ChatInfo of(Trade trade, ChatMessage message) {
        Goods goods = trade.getGoods();

        if (message == null) {
            return new ChatInfo(
                    trade.getId(),
                    goods.getId(),
                    goods.getName(),
                    null,
                    null,
                    goods.getCurrParticipants(),
                    goods.getMainImageName()
            );
        }
        return new ChatInfo(
                trade.getId(),
                goods.getId(),
                goods.getName(),
                message.getMessage(),
                message.getCreatedAt(),
                goods.getCurrParticipants(),
                goods.getMainImageName()
        );
    }
}
