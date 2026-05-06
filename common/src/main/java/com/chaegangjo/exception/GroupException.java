package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.GroupErrorCode;

public class GroupException extends BaseException {

    public GroupException(GroupErrorCode errorCode) {
        super(errorCode);
    }
}
