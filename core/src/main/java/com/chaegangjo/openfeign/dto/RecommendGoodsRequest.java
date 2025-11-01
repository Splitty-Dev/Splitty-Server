package com.chaegangjo.openfeign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RecommendGoodsRequest(
        @JsonProperty("user_id")
        String userId,
        @JsonProperty("top_n")
        int topN,
        @JsonProperty("available_items")
        List<Long> availableItems) {
}
