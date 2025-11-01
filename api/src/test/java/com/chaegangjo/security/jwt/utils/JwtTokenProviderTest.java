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
        TokenInfo token = jwtTokenProvider.issueAccessToken("email", 7L);
        System.out.println(token.accessToken());
    }
}