package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.BaseErrorCode;

public class TradeMemberException extends BaseException{

    public TradeMemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}