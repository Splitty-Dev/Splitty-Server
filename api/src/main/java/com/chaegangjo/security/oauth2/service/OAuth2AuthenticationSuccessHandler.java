package com.chaegangjo.security.oauth2.service;

import com.chaegangjo.jwt.JwtProperties;
import com.chaegangjo.jwt.TokenInfo;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.security.jwt.utils.JwtTokenProvider;
import com.chaegangjo.security.oauth2.dto.OAuth2UserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Value("${spring.security.oauth2.client.front-redirect-url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws
            IOException, ServletException {

        OAuth2UserImpl principal = (OAuth2UserImpl) authentication.getPrincipal();
        Member member = principal.getMember();

        TokenInfo tokenInfo = jwtTokenProvider.issueAccessToken(member.getEmail(), member.getId());

//        ResponseCookie cookie = ResponseCookie.from("accessToken", tokenInfo.accessToken())
//                .maxAge(jwtProperties.getAccessExpirationTime())
//                .path("/")
//                .sameSite("None")
//                .secure(true) // SameSite=None일 때는 Secure 필수
//                .build();

        String fullRedirectUrl = UriComponentsBuilder
                .fromUriString(redirectUrl)
                .queryParam("accessToken", tokenInfo.accessToken())
                .build()
                .toUriString();

        log.info(redirectUrl);
//        response.addHeader("Set-Cookie", cookie.toString());
        response.sendRedirect(fullRedirectUrl);
    }
}
