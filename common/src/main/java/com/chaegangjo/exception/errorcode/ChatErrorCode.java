package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ChatErrorCode implements BaseErrorCode{
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT-001", "채팅 메시지를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}
