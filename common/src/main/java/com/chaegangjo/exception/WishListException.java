package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.WishListErrorCode;

public class WishListException extends BaseException{

    public WishListException(WishListErrorCode errorCode) {
        super(errorCode);
    }
}
