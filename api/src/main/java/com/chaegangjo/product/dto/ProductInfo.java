package com.chaegangjo.product.dto;

import com.chaegangjo.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductInfo {

    @Schema(description = "상품명", example = "코카콜라 1.5L 24개입")
    private final String name;

    @Schema(description = "수량", example = "24")
    private final int quantity;

    @Schema(description = "가격", example = "28900")
    private final Integer price;

    @Schema(description = "평점", example = "4.5")
    private final Float rating;

    @Schema(description = "리뷰 수", example = "1203")
    private final Integer reviewCount;

    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private final String imageUrl;

    @JsonCreator
    private ProductInfo(
            @JsonProperty("name") String name,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("price") Integer price,
            @JsonProperty("rating") Float rating,
            @JsonProperty("reviewCount") Integer reviewCount,
            @JsonProperty("imageUrl") String imageUrl) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imageUrl = imageUrl;
    }

    public static ProductInfo from(Product product) {
        return new ProductInfo(
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getRating(),
                product.getReviewCount(),
                product.getImageUrl()
        );
    }
}
