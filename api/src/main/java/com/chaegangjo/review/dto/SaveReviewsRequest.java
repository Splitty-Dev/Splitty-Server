package com.chaegangjo.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record SaveReviewsRequest (
        @Schema(example = "1")
        Long goodsId, List<SaveReviewInfo> reviews) {

        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @Getter
        public static class SaveReviewInfo {
            @Schema(example = "2")
            private Long revieweeId;
            @Schema(example = "4.5")
            private float rating;
            @Schema(example = "정말 친절하고 빠르게 거래했어요!")
            private String content;
        }
}