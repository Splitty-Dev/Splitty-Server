package com.chaegangjo.firebase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmMessageTemplate {
    NEW_PARTICIPANT("새로운 참여자가 들어왔어요.", "조금만 더 모이면 모집이 완료돼요!"),
    TRADE_CLOSED("모집이 완료되었어요.", "모집이 완료되었어요. 곧 거래 일정이 확정됩니다!"),
    TRADE_COMPLETED("거래가 완료되었어요.", "거래가 성공적으로 완료되었어요. 후기를 남겨보세요!");

    private final String title;
    private final String body;
}
