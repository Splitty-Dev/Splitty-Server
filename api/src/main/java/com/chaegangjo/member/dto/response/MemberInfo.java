package com.chaegangjo.member.dto.response;


import com.chaegangjo.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfo(
        @Schema(example = "1")
        Long id,
        @Schema(example = "귀여운고양이35")
        String username,
        @Schema(example = "3.5")
        float rating,
        @Schema(example = "공릉동")
        String neighName,
        @Schema(example = "https://image.jpg")
        String profileImageUrl
) {

    public static MemberInfo from(Member member) {
        return new MemberInfo(
                member.getId(),
                member.getUsername(),
                member.getRating(),
                member.getNeighName(),
                member.getProfileImageUrl()
        );
    }
}
