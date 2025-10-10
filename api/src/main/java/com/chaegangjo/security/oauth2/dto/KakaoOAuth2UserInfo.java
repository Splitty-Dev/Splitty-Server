package com.chaegangjo.security.oauth2.dto;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private final Map<String, Object> account;
    private final Map<String, Object> profile;
    private final String nameAttributeKey;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes, String nameAttributeKey) {
        super(attributes);
        this.account = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, Object>) account.get("profile");
        this.nameAttributeKey = nameAttributeKey;
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get(nameAttributeKey));
    }

    @Override
    public String getEmail() {
        return String.valueOf(account.get("email"));
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(profile.get("profile_image_url"));
    }
}
