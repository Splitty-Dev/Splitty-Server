package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GroupErrorCode implements BaseErrorCode {

    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "GROUP-001", "존재하지 않는 그룹입니다."),
    INVALID_JOIN_CODE(HttpStatus.BAD_REQUEST, "GROUP-002", "유효하지 않은 참여 코드입니다."),
    ALREADY_JOINED_GROUP(HttpStatus.CONFLICT, "GROUP-003", "이미 참여한 그룹입니다."),
    NOT_GROUP_MEMBER(HttpStatus.FORBIDDEN, "GROUP-004", "그룹 멤버가 아닙니다."),
    GROUP_FULL(HttpStatus.CONFLICT, "GROUP-005", "그룹 정원이 가득 찼습니다."),
    GROUP_CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "GROUP-006", "그룹 채팅방을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String value;
    private final String message;
}
