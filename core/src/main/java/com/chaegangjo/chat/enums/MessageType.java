package com.chaegangjo.chat.enums;

import lombok.Getter;

@Getter
public enum MessageType {
    TEXT(""),
    ENTER("님이 거래에 참여하셨습니다."),
    LEAVE("님이 거래에서 나가셨습니다.");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }
}
