package com.chaegangjo.exception.errorcode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum KeywordNotificationErrorCode implements BaseErrorCode {

    KEYWORD_NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "KEYWORD-NOTIFICATION-001", "존재하지 않는 키워드 알림입니다."),
    OWNER_MISMATCH(HttpStatus.BAD_REQUEST, "KEYWORD-NOTIFICATION-002", "본인의 키워드 알림만 삭제할 수 있습니다."),
    BLANK_KEYWORD(HttpStatus.BAD_REQUEST, "KEYWORD-NOTIFICATION-003", "키워드는 공백일 수 없습니다."),
    DUPLICATE_KEYWORD(HttpStatus.CONFLICT, "KEYWORD-NOTIFICATION-004", "이미 등록된 키워드입니다."),
    KEYWORD_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "KEYWORD-NOTIFICATION-005", "등록 가능한 키워드 개수를 초과했습니다.")
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}
