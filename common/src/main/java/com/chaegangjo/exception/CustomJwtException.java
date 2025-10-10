package com.chaegangjo.exception;

import lombok.Getter;

@Getter
public class CustomJwtException extends BaseException {

	public CustomJwtException(JwtErrorCode errorCode) {
		super(errorCode);
	}
}
