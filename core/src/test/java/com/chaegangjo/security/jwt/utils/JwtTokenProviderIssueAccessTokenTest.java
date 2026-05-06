package com.chaegangjo.security.jwt.utils;

import com.chaegangjo.jwt.JwtProperties;
import com.chaegangjo.jwt.TokenInfo;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtTokenProviderIssueAccessTokenTest {

    @Test
    void issuesAccessTokenForMemberId() {
        JwtProperties properties = new JwtProperties();
        properties.setSecretKey("MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=");
        properties.setAccessExpirationTime(600_000L);

        JwtTokenProvider provider = new JwtTokenProvider(properties);
        provider.initializeSecretKey();

        Long memberId = 2L;
        String email = "user@example.com";

        TokenInfo tokenInfo = provider.issueAccessToken(email, memberId);

        assertNotNull(tokenInfo);
        assertEquals(memberId, tokenInfo.id());
        assertEquals(email, tokenInfo.email());
        assertNotNull(tokenInfo.accessToken());

        Claims claims = provider.validateToken(tokenInfo.accessToken());
        assertEquals(memberId, provider.getId(claims));
        assertEquals(email, provider.getEmail(claims));
    }
}

