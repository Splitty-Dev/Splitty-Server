package com.chaegangjo.openfeign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RecommendGoodsResponse(
        @JsonProperty("user_id")
        Long userId,
        List<RankInfo> items,
        @JsonProperty("has_next")
        boolean hasNext
) {

    public record RankInfo(
            @JsonProperty("item_id")
            Long itemId,
            Long rank
    ) {
    }
}
