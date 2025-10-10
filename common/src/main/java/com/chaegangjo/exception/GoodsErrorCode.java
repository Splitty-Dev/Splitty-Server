package com.chaegangjo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GoodsErrorCode implements BaseErrorCode {

    GOODS_NOT_FOUND(HttpStatus.NOT_FOUND, "GOODS-001", "존재하지 않는 상품입니다."),
            ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}
