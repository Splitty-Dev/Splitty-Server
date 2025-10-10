package com.chaegangjo.security.oauth2.service;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.enums.SocialType;
import com.chaegangjo.member.repository.MemberRepository;
import com.chaegangjo.security.oauth2.dto.OAuth2UserImpl;
import com.chaegangjo.security.oauth2.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);  // OAuth2 정보 조회

        // OAuth2 서비스 id (kakao, ...)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = SocialType.getSocialType(registrationId);

        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


        Map<String, Object> originAttributes = oAuth2User.getAttributes();
        OAuthAttributes attributes = OAuthAttributes.of(socialType, nameAttributeKey, originAttributes);
        Member member = getOrSave(attributes, socialType);

        return new OAuth2UserImpl(originAttributes, member);
    }

    private Member getOrSave(OAuthAttributes attributes, SocialType socialType) {
        String socialId = attributes.getOAuth2UserInfo().getId();

        return memberRepository.findBySocialId(socialId)
                .orElseGet(() -> memberRepository.save(attributes.toEntity(socialType)));

    }
}