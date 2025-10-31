package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FirebaseErrorCode implements BaseErrorCode {
    FIREBASE_INITIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE-001", "Firebase 앱을 초기화할 수 없습니다."),
    FCM_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "FIREBASE-002", "FCM 토큰을 찾을 수 없습니다."),
    FCM_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE-003", "FCM 메시지 전송에 실패했습니다.")
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}
