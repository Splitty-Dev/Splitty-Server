package com.chaegangjo.member.enums;

import lombok.Getter;

@Getter
public enum SocialType {
    KAKAO;

    public static SocialType getSocialType(String registrationId) {

        if (registrationId.equalsIgnoreCase(SocialType.KAKAO.name())) {
            return SocialType.KAKAO;
        } else {
            return null;
        }
    }
}
