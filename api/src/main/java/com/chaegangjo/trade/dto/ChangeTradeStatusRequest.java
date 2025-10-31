package com.chaegangjo.trade.dto;

import com.chaegangjo.goods.enums.TradeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeTradeStatusRequest(
        @Schema(example = "1")
        Long goodsId,
        @Schema(example = "COMPLETED", description = "OPEN, CLOSED, COMPLETED")
        TradeStatus tradeStatus
) {
}
