package com.chaegangjo.goods.dto.request;

import com.chaegangjo.goods.domain.Category;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SaveGoodsRequest(
        @Schema(example = "1")
        Long categoryId,
        @Schema(example = "에코 생수 500ml")
        String name,
        @Schema(example = "위치 협의 가능합니다.")
        String description,
        @Schema(example = "6000")
        int totalPrice,
        @Schema(example = "10")
        int totalQuantity,
        @Schema(example = "8")
        int leftQuantity,
        @Schema(example = "성수역 3번 출구")
        String preferredLocation,
        @Schema(example = "[\"image1.jpg\", \"image2.jpg\"]")
        List<String> imageNames) {

    public Goods toEntity(Member seller, Category category) {
        String mainImageName = imageNames.isEmpty() ? null : imageNames.getFirst();
        return Goods.builder()
                .category(category)
                .name(name)
                .description(description)
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .leftQuantity(leftQuantity)
                .preferredLocation(preferredLocation)
                .mainImageName(mainImageName)
                .seller(seller)
                .build();
    }
}
