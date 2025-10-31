package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TradeErrorCode implements BaseErrorCode {
    TRADE_NOT_FOUND(HttpStatus.NOT_FOUND, "TRADE-001", "존재하지 않는 거래입니다."),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "TRADE-002", "상품 재고가 부족합니다."),
    ALREADY_JOINED(HttpStatus.BAD_REQUEST, "TRADE-003", "이미 참여한 거래입니다."),
    TRADE_NOT_OPENED(HttpStatus.BAD_REQUEST, "TRADE-004", "모집이 완료된 거래입니다."),
    TOTAL_QUANTITY_MISMATCH(HttpStatus.BAD_REQUEST, "TRADE-005", "총 수량이 일치하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}