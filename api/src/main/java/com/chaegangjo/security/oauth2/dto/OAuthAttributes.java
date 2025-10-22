package com.chaegangjo.security.oauth2.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.enums.Role;
import com.chaegangjo.member.enums.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * social별 분기 처리
 */
@Getter
public class OAuthAttributes {

    private final String nameAttributeKey;
    private final OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String nameAttributeKey, Map<String, Object> attributes) {
        if (socialType == SocialType.KAKAO) {
            return ofKakao(nameAttributeKey, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofKakao(String nameAttributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes, nameAttributeKey))
                .build();
    }

    public Member toEntity(SocialType socialType) {
        return Member.builder()
                .role(Role.USER)
                .socialId(oAuth2UserInfo.getId())
                .socialType(socialType)
                .email(oAuth2UserInfo.getEmail())
                .profileImageUrl(oAuth2UserInfo.getImageUrl())
                .build();
    }
}
