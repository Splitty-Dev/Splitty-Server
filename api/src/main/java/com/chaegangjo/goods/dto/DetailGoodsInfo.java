package com.chaegangjo.goods.dto;


import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.GoodsImage;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.dto.response.MemberInfo;
import com.chaegangjo.utils.S3Utils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record DetailGoodsInfo(
        @Schema(example = "1")
        Long id,
        MemberInfo seller,
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
        List<String> imageUrls
) {

    public static DetailGoodsInfo from(Goods goods) {
        return new DetailGoodsInfo(
                goods.getId(),
                MemberInfo.from(goods.getSeller()),
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
                getImageUrls(goods, goods.getImages())
        );
    }

    public static DetailGoodsInfo of(Goods goods, List<GoodsImage> images) {
        return new DetailGoodsInfo(
                goods.getId(),
                MemberInfo.from(goods.getSeller()),
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
                getImageUrls(goods, images)
        );
    }

    public static List<String> getImageUrls(Goods goods, List<GoodsImage> images) {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(S3Utils.getImageUrl(goods.getMainImageName()));

        List<String> subImageUrls = images.stream()
                .map(image -> S3Utils.getImageUrl(image.getImageName()))
                .collect(Collectors.toList());
        imageUrls.addAll(subImageUrls);

        return imageUrls;
    }
}
