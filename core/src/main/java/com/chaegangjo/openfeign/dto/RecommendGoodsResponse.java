package com.chaegangjo.openfeign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RecommendGoodsResponse(
        @JsonProperty("user_id")
        Long userId,
        List<RankInfo> items
) {

    public record RankInfo(
            @JsonProperty("item_id")
            Long itemId,
            int rank
    ) {
    }
}
