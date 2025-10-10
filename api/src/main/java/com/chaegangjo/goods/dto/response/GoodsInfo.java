package com.chaegangjo.goods.dto.response;


import com.chaegangjo.goods.domain.Goods;
import io.swagger.v3.oas.annotations.media.Schema;

public record GoodsInfo(
        @Schema(example = "1")
        Long id,
        @Schema(description = "상품명", example = "에코 생수 500ml")
        String name,
        @Schema(description = "1개당 가격", example = "600")
        int price, // 1개당 가격
        @Schema(description = "공릉동")
        String neighName,
        @Schema(description = "남은 수량", example = "5")
        int leftQuantity,
        @Schema(description = "총 수량", example = "10")
        int quantity,
        @Schema(description = "현재 참여 인원", example = "3")
        int currParticipants,
        @Schema(example = "https://image.jpg")
        String image
) {

    public static GoodsInfo from(Goods goods) {
        return new GoodsInfo(
                goods.getId(),
                goods.getName(),
                (int) Math.ceil((double) goods.getTotalPrice() / goods.getQuantity()),
                goods.getNeighName(),
                goods.getLeftQuantity(),
                goods.getQuantity(),
                goods.getCurrParticipants(),
                null
//                goods.getImages().getFirst().getImageUrl()
        );
    }
}