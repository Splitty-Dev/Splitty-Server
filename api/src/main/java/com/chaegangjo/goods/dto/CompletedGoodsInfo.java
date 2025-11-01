package com.chaegangjo.goods.dto;


import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.utils.S3Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CompletedGoodsInfo extends GoodsInfo {

    boolean isReviewed;

    public CompletedGoodsInfo(Long id, String name, int price, String neighName, int leftQuantity, int quantity,
                              int currParticipants, String imageUrlPrefix, String imageName, Long sellerId,
                              TradeStatus status, int totalWishlist, boolean isReviewed) {
        super(id, name, price, neighName, leftQuantity, quantity, currParticipants, imageUrlPrefix, imageName,
                sellerId, status, totalWishlist);
        this.isReviewed = isReviewed;
    }

    public static CompletedGoodsInfo of(Goods goods, boolean isReviewed) {
        return new CompletedGoodsInfo(
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
                goods.getTotalWishlist(),
                isReviewed
        );
    }
}