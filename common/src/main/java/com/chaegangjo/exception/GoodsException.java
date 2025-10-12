package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.GoodsErrorCode;

public class GoodsException extends BaseException {

    public GoodsException(GoodsErrorCode errorCode) { super(errorCode);}
}
