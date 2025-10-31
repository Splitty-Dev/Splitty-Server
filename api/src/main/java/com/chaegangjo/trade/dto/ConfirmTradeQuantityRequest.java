package com.chaegangjo.trade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record ConfirmTradeQuantityRequest(
        @Schema(example = "1")
        Long goodsId,
        List<ConfirmTradeQuantityInfo> quantities
) {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ConfirmTradeQuantityInfo {

        @Schema(example = "1")
        Long memberId;
        @Schema(example = "2")
        int quantity;
    }
}
