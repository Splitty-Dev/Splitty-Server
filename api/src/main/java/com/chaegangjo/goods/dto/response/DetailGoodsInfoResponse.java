package com.chaegangjo.goods.dto.response;


import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.GoodsImage;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.dto.response.MemberInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DetailGoodsInfoResponse(
        @Schema(example = "1")
        Long id,
        MemberInfoResponse seller,
        @Schema(example = "식품")
        String category,
        @Schema(example = "성수동")
        String neighName,
        @Schema(example = "에코 생수 500ml")
        String name,
        @Schema(example = "위치 협의 가능합니다.")
        String description,
        @Schema(example = "OPEN")
        TradeStatus status,
        @Schema(example = "1600")
        int unitPrice,
        @Schema(example = "33")
        int viewCount,
        @Schema(example = "7")
        int leftQuantity ,
        @Schema(example = "2")
        int currParticipants,
        @Schema(example = "성수역 3번 출구")
        String preferredLocation,
        @Schema(example = "[\"http://image1.jpg\", \"http://image2.jpg\"]")
        List<String> images
) {

    public static DetailGoodsInfoResponse from(Goods goods) {
        return new DetailGoodsInfoResponse(
                goods.getId(),
                MemberInfoResponse.from(goods.getSeller()),
                goods.getCategory().getName(),
                goods.getNeighName(),
                goods.getName(),
                goods.getDescription(),
                goods.getStatus(),
                goods.getUnitPrice(),
                goods.getViewCount(),
                goods.getLeftQuantity(),
                goods.getCurrParticipants(),
                goods.getPreferredLocation(),
                goods.getImages().stream()
                        .map(GoodsImage::getImageUrl).toList()
        );
    }
}
