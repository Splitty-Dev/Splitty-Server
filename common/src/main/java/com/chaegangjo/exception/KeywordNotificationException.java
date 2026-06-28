package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.KeywordNotificationErrorCode;

public class KeywordNotificationException extends BaseException {

    public KeywordNotificationException(KeywordNotificationErrorCode errorCode) {
        super(errorCode);
    }
}
