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
        @Schema(example = "https://bucket.amazonaws.com/")
        String imageUrlPrefix,
        @Schema(example = "[\"image1.jpg\", \"image2.jpg\"]")
        List<String> imageName,
        @Schema(example = "30")
        int totalWishlist
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
                S3Utils.S3_BUCKET_URL_PREFIX,
                getImageName(goods, goods.getImages()),
                goods.getTotalWishlist()
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
                S3Utils.S3_BUCKET_URL_PREFIX,
                getImageName(goods, images),
                goods.getTotalWishlist()
        );
    }

    public static List<String> getImageName(Goods goods, List<GoodsImage> images) {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(goods.getMainImageName());
        List<String> subImageUrls = images.stream()
                .map(GoodsImage::getImageName)
                .collect(Collectors.toList());
        imageUrls.addAll(subImageUrls);
        return imageUrls;
    }
}
