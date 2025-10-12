package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.MemberErrorCode;

public class MemberException extends BaseException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
