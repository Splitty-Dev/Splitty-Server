package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.JwtErrorCode;
import lombok.Getter;

@Getter
public class CustomJwtException extends BaseException {

	public CustomJwtException(JwtErrorCode errorCode) {
		super(errorCode);
	}
}
