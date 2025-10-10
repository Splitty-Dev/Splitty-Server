package com.chaegangjo.goods.dto.response;


import com.chaegangjo.goods.domain.Goods;

public record GoodsInfo(
        Long id,
        String name,
        int price, // 1개당 가격
        String neighName,
        int leftQuantity,
        int quantity,
        int currParticipants,
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