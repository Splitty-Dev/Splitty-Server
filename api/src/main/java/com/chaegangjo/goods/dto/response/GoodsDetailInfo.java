package com.chaegangjo.goods.dto.response;


import com.chaegangjo.goods.domain.Category;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.dto.response.MemberInfo;

import java.util.List;

public record GoodsDetailInfo(
        Long id,
        MemberInfo seller,
        Category category,
        String neighName,
        String name,
        String description,
        TradeStatus status,
        int price,
        int viewCount,
        int quantity,
        int maxParticipants,
        int currParticipants,
        List<String> images,
        String preferredLocation
) {
}
