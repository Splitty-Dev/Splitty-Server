package com.chaegangjo.review.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record ReviewInfo(
                    @Schema(description = "리뷰어 정보")
                    MemberInfo reviewer,
                    @Schema(description = "리뷰 작성일시", example = "2023-10-05T14:48:00")
                    LocalDateTime createdAt,
                    @Schema(example = "정말 친절하고 빠르게 거래했어요!")
                    String content) {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MemberInfo {
        @Schema(description = "리뷰어 ID", example = "1")
        private Long reviewerId;
        @Schema(example = "귀여운다람쥐323")
        private String username;
        @Schema(example = "https://example.com/profile.jpg")
        private String profileImageUrl;

        public MemberInfo(Long reviewerId, String username, String profileImageUrl) {
            this.reviewerId = reviewerId;
            this.username = username;
            this.profileImageUrl = profileImageUrl;
        }

        public static MemberInfo from(Member member) {
            return new MemberInfo(
                    member.getId(),
                    member.getUsername(),
                    member.getProfileImageUrl()
            );
        }
    }

    public static ReviewInfo from(Review review) {
        MemberInfo memberInfo = MemberInfo.from(review.getReviewer().getMember());
        return new ReviewInfo(memberInfo, review.getCreatedAt(), review.getContent());
    }
}
