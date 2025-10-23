package com.chaegangjo.trade.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record JoinTradeRequest(
        @Schema(example = "1")
        Long goodsId,
        @Schema(example = "3")
        int quantity) {
}
