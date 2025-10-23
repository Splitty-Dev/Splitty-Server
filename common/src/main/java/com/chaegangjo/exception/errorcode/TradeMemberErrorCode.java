package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TradeMemberErrorCode implements BaseErrorCode {
    TRADE_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "TRADE-MEMBER-001", "존재하지 않는 거래회원입니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}