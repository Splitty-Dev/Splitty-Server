package com.chaegangjo.security.jwt.filter;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.exception.CustomJwtException;
import com.chaegangjo.exception.JwtErrorCode;
import com.chaegangjo.security.JsonResponseWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.chaegangjo.exception.JwtErrorCode.JWT_ERROR;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (CustomJwtException e) {
			log.warn("CustomJwtException: {}", e);

			ApiResponse<Object> errorResponse = ApiResponse.failure(e.getErrorCode());

			JsonResponseWriter.sendErrorResponse(
				response,
				e.getErrorCode().getStatus(),
				errorResponse
			);
		} catch (Exception e) {
			log.error("JwtException: {}", e);

			JwtErrorCode errorCode = JWT_ERROR;

			ApiResponse<Object> errorResponse = ApiResponse.failure(
				errorCode.getValue(),
				errorCode.getMessage()
			);

			JsonResponseWriter.sendErrorResponse(
				response,
				errorCode.getStatus(),
				errorResponse
			);
		}
	}
}
