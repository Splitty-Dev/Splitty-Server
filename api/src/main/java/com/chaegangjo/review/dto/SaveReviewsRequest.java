package com.chaegangjo.review.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SaveReviewsRequest {

    private Long tradeId;
    private List<ReviewInfo> reviews;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ReviewInfo {
        private Long revieweeId;
        private int rating;
        private String content;
    }
}