package com.chaegangjo.interceptor;

import com.chaegangjo.jwt.JwtProperties;
import com.chaegangjo.security.jwt.utils.JwtTokenAuthenticator;
import com.chaegangjo.security.jwt.utils.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenAuthenticator jwtTokenAuthenticator;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message); //헤더 정보 추출

        if (accessor.getCommand() == StompCommand.CONNECT || accessor.getCommand() == StompCommand.SEND) {
            String token = extractToken(accessor);
            if (token != null) {
                Claims claims = jwtTokenProvider.validateToken(token);
                Authentication authentication = jwtTokenAuthenticator.getAuthentication(claims);
                accessor.setUser(authentication);
            }
        }

        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }

    private String extractToken(StompHeaderAccessor accessor) {
        String header = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return null;
        }
        return header.split(" ")[1];
    }
}