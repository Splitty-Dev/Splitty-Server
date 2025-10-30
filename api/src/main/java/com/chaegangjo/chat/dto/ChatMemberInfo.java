package com.chaegangjo.chat.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.chat.domain.ChatMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record ChatMemberInfo(Long id,
                             @Schema(example = "재밌는원숭이31")
                            String username,
                             @Schema(example = "https://profile-image.png")
                            String profileImageUrl) {

    @Builder
    public ChatMemberInfo(Long id, String username, String profileImageUrl) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }

    public static ChatMemberInfo from(ChatMember chatMember) {
        Member member = chatMember.getMember();
        return ChatMemberInfo.builder()
                .id(member.getId())
                .username(member.getUsername())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
