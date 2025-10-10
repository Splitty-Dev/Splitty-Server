package com.chaegangjo.goods.dto.request;


import com.chaegangjo.goods.domain.Category;

public record CreateGoods(
        Category category,
        String name,
        String description,
        int price,
        int quantity,
        int maxParticipants,
        String preferredLocation
) {
}
