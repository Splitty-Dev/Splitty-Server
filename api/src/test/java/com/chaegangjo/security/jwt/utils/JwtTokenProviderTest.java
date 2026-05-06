package com.chaegangjo.security.jwt.utils;

import com.chaegangjo.jwt.TokenInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void name() {
        for (int i = 1; i < 6; i++) {
            TokenInfo token = jwtTokenProvider.issueAccessToken(i + "test.com", (long) i);
            System.out.println(token.accessToken());
        }
    }
}