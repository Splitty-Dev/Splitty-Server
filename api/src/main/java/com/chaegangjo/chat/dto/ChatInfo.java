package com.chaegangjo.chat.dto;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.chat.domain.ChatMessage;
import java.time.LocalDateTime;

public record ChatInfo(
        Long goodsId,
        String name,
        String lastMessage,
        LocalDateTime updatedAt,
        int currParticipants,
        String imageName
) {

    public static ChatInfo of(Goods goods, ChatMessage message) {

        if (message == null) {
            return new ChatInfo(
                    goods.getId(),
                    goods.getName(),
                    null,
                    null,
                    goods.getCurrParticipants(),
                    goods.getMainImageName()
            );
        }
        return new ChatInfo(
                goods.getId(),
                goods.getName(),
                message.getMessage(),
                message.getCreatedAt(),
                goods.getCurrParticipants(),
                goods.getMainImageName()
        );
    }
}
