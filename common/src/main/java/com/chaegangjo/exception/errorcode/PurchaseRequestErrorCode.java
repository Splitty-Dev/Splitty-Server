package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PurchaseRequestErrorCode implements BaseErrorCode {

    PURCHASE_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "PURCHASE-001", "존재하지 않는 구매 요청입니다."),
    PURCHASE_REQUEST_CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "PURCHASE-002", "존재하지 않는 채팅방입니다."),
    CANNOT_CHAT_WITH_SELF(HttpStatus.BAD_REQUEST, "PURCHASE-003", "본인의 구매 요청에는 채팅을 보낼 수 없습니다."),
    UNAUTHORIZED_CHAT_ROOM(HttpStatus.FORBIDDEN, "PURCHASE-004", "해당 채팅방에 접근 권한이 없습니다.")
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}
