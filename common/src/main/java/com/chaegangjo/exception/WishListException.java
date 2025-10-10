package com.chaegangjo.exception;

public class WishListException extends BaseException{

    public WishListException(WishListErrorCode errorCode) {
        super(errorCode);
    }
}
