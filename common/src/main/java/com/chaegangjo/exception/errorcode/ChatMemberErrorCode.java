package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatMemberErrorCode implements BaseErrorCode {
    TRADE_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT-MEMBER-001", "거래에 참여하는 회원이 아닙니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}