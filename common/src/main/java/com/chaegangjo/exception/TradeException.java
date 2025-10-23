package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.TradeErrorCode;

public class TradeException extends BaseException{

    public TradeException(TradeErrorCode errorCode) {
        super(errorCode);
    }
}