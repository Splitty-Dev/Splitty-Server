package com.chaegangjo.group.dto;

import com.chaegangjo.group.domain.GroupChatMember;
import com.chaegangjo.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record GroupChatMemberInfo(
        @Schema(example = "5")
        Long memberId,

        @Schema(example = "재밌는원숭이31")
        String username,

        @Schema(example = "https://profile-image.png")
        String profileImageUrl
) {
    public static GroupChatMemberInfo from(GroupChatMember chatMember) {
        Member member = chatMember.getMember();
        return new GroupChatMemberInfo(member.getId(), member.getUsername(), member.getProfileImageUrl());
    }
}
