package com.chaegangjo.security.jwt.filter;

import com.chaegangjo.jwt.JwtTokenProvider;
import com.chaegangjo.security.jwt.JwtTokenAuthenticator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenAuthenticator jwtTokenAuthenticator;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		String token = extractToken(request);

		if (token != null) {
			Claims claims = jwtTokenProvider.validateToken(token);
			Authentication authentication = jwtTokenAuthenticator.getAuthentication(claims);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	public String extractToken(HttpServletRequest request) {
		String token = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("accessToken".equals(cookie.getName())) {
					token = cookie.getValue();
				}
			}
		}

		return token;
	}
}
