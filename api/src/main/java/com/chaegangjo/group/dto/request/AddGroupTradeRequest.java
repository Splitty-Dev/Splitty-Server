package com.chaegangjo.group.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AddGroupTradeRequest(
        @Schema(description = "거래명", example = "2024년 12월 장보기")
        String name,

        @Schema(description = "거래 물품 목록")
        List<TradeItemRequest> items
) {
    public record TradeItemRequest(
            @Schema(description = "물품명", example = "에코 생수 500ml 24개")
            String itemName,

            @Schema(description = "가격 (원)", example = "12000")
            int price
    ) {
    }
}
