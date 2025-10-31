package com.chaegangjo.chat.dto;

import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.goods.domain.Goods;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatInfo(
        @Schema(example = "1")
        Long goodsId,
        @Schema(example = "에코 생수 500ml")
        String name,
        @Schema(example = "안녕하세요, 이 상품 아직 있나요?")
        String lastMessage,
        @Schema(example = "2024-05-01T12:34:56")
        LocalDateTime updatedAt,
        @Schema(example = "3")
        int currParticipants,
        @Schema(example = "image.jpg")
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
