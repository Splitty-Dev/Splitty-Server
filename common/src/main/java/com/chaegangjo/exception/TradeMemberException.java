package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.TradeMemberErrorCode;

public class TradeMemberException extends BaseException{

    public TradeMemberException(TradeMemberErrorCode errorCode) {
        super(errorCode);
    }
}