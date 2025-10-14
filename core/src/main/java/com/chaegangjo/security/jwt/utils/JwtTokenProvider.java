package com.chaegangjo.security.jwt.utils;


import com.chaegangjo.exception.CustomJwtException;
import com.chaegangjo.exception.errorcode.JwtErrorCode;
import com.chaegangjo.jwt.JwtProperties;
import com.chaegangjo.jwt.TokenInfo;
import com.chaegangjo.member.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
	private SecretKey secretKey;

	@PostConstruct
	public void initializeSecretKey() {
		byte[] decoded = Decoders.BASE64.decode(jwtProperties.getSecretKey());
		this.secretKey = Keys.hmacShaKeyFor(decoded);
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

	public TokenInfo issueAccessToken(String email, Long id) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(jwtProperties.getAccessExpirationTime());
		String token = buildJwtToken(email, id, issuedAt, expiration);

		return new TokenInfo(email, id, token);
	}

	public String buildJwtToken(String email, Long id, Instant issuedAt, Instant expiration) {
		return Jwts.builder()
			.setSubject(String.valueOf(id))
			.claim("email", email)
			.claim("role", Role.USER)
			.setIssuedAt(Date.from(issuedAt))
			.setExpiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();
	}

	//JWT 토큰 검증
	public Claims validateToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey).build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
//			log.warn("[!] Expired Token: {}", e.getMessage());
			throw new CustomJwtException(JwtErrorCode.EXPIRED_TOKEN);
		} catch (MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
//			log.warn("[!] Invalid Token: {}", e.getMessage());
			throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN);
		} catch (Exception e) {
//			log.error("[!] Unexpected JWT Error: {}", e.getMessage());
			throw new CustomJwtException(JwtErrorCode.JWT_ERROR);
		}
	}

	public String getSubject(Claims claims) { // 현재 subject -> id
		return claims.getSubject();
	}

	public Long getId(Claims claims) {
		return Long.parseLong(claims.getSubject());
	}

	public String getEmail(Claims claims) {
		return claims.get("email", String.class);
	}

	public String getAuthority(Claims claims) {
		return claims.get("role", String.class);
	}
}
