package com.chaegangjo.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum WishListErrorCode implements BaseErrorCode {

    WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "WISHLIST-001", "존재하지 않는 관심상품입니다."),
    WISH_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "WISHLIST-002", "이미 관심상품에 저장된 상품입니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}
