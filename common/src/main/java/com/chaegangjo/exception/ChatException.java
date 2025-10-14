package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.BaseErrorCode;

public class ChatException extends BaseException{

    public ChatException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
