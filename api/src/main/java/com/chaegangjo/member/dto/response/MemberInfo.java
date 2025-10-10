package com.chaegangjo.member.dto.response;


import com.chaegangjo.member.domain.Member;

public record MemberInfo(
        Long id,
        String username,
        float rating,
        String neighName,
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
