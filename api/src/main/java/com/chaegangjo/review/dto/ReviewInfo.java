package com.chaegangjo.review.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.review.domain.Review;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record ReviewInfo(MemberInfo memberInfo, LocalDateTime createdAt, String content) {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    private static class MemberInfo {
        private Long reviewerId;
        private String username;
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
