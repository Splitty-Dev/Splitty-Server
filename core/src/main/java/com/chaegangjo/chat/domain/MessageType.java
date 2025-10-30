package com.chaegangjo.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum MessageType {
    TEXT(""),
    ENTER("님이 입장하셨습니다."),
    LEAVE("님이 퇴장하셨습니다.");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }
}
