package com.chaegangjo.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "존재하지 않는 회원입니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}