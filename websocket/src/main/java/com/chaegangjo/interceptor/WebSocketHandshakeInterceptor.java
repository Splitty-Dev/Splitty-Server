//package com.chaegangjo.interceptor;
//
//import com.chaegangjo.security.jwt.utils.JwtTokenAuthenticator;
//import com.chaegangjo.security.jwt.utils.JwtTokenProvider;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtTokenAuthenticator jwtTokenAuthenticator;
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        //access token 검증
//        if (request instanceof ServletServerHttpRequest servletRequest) {
//            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
//
//            String token = jwtTokenProvider.extractToken(httpServletRequest);
//
//            if (token != null) {
//                Claims claims = jwtTokenProvider.validateToken(token);
//                Authentication authentication = jwtTokenAuthenticator.getAuthentication(claims);
//
//                SecurityContextHolder.getContext().setAuthentication(authentication); //security context에 authentication 정보 저장
//            } else {
//                throw new CustomJwtException(UNAUTHORIZED_ACCESS);
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//    }
//}
