package com.chaegangjo.security.oauth2.dto;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.enums.Role;
import com.chaegangjo.member.enums.SocialType;
import java.util.Set;
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
    private static final Set<String> KAKAO_DEFAULT_IMAGE_URL = Set.of(
            "http://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg",
            "http://img2.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg",
            "http://img3.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg",
            "http://img4.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg"
    );
    private static final String DEFAULT_PROFILE_IMAGE_URL = "https://splitty-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.jpg";

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
        String imageUrl = oAuth2UserInfo.getImageUrl();
        if (KAKAO_DEFAULT_IMAGE_URL.contains(imageUrl)) {
            imageUrl = DEFAULT_PROFILE_IMAGE_URL;
        }
        return Member.builder()
                .role(Role.USER)
                .socialId(oAuth2UserInfo.getId())
                .socialType(socialType)
                .email(oAuth2UserInfo.getEmail())
                .profileImageUrl(imageUrl)
                .build();
    }
}
