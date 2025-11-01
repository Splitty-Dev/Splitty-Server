package com.chaegangjo.goods.dto;


import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.utils.S3Utils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoodsInfo {
    @Schema(example = "1")
    Long id;
    @Schema(description = "상품명", example = "에코 생수 500ml")
    String name;
    @Schema(description = "1개당 가격", example = "600")
    int price; // 1개당 가격
    @Schema(description = "법정동", example = "공릉동")
    String neighName;
    @Schema(description = "남은 수량", example = "5")
    int leftQuantity;
    @Schema(description = "총 수량", example = "10")
    int quantity;
    @Schema(description = "현재 참여 인원", example = "3")
    int currParticipants;
    @Schema(example = "https://bucket.amazonaws.com/")
    String imageUrlPrefix;
    @Schema(example = "image.jpg")
    String imageName;
    @Schema(example = "1")
    Long sellerId;
    @Schema(description = "거래 상태", example = "OPEN")
    TradeStatus status;
    @Schema(example = "15")
    int totalWishlist;

    public GoodsInfo(Long id, String name, int price, String neighName, int leftQuantity, int quantity,
                     int currParticipants, String imageUrlPrefix, String imageName, Long sellerId, TradeStatus status,
                     int totalWishlist) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.neighName = neighName;
        this.leftQuantity = leftQuantity;
        this.quantity = quantity;
        this.currParticipants = currParticipants;
        this.imageUrlPrefix = imageUrlPrefix;
        this.imageName = imageName;
        this.sellerId = sellerId;
        this.status = status;
        this.totalWishlist = totalWishlist;
    }

    public static GoodsInfo from(Goods goods) {
        return new GoodsInfo(
                goods.getId(),
                goods.getName(),
                (int) Math.ceil((double) goods.getTotalPrice() / goods.getTotalQuantity()),
                goods.getNeighName(),
                goods.getLeftQuantity(),
                goods.getTotalQuantity(),
                goods.getCurrParticipants(),
                S3Utils.S3_BUCKET_URL_PREFIX,
                goods.getMainImageName(),
                goods.getSeller().getId(),
                goods.getStatus(),
                goods.getTotalWishlist()
        );
    }
}