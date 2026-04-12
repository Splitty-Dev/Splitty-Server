package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.PurchaseRequestErrorCode;

public class PurchaseRequestException extends BaseException {

    public PurchaseRequestException(PurchaseRequestErrorCode errorCode) {
        super(errorCode);
    }
}
