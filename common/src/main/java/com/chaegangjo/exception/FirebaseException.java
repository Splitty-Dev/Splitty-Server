package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.FirebaseErrorCode;

public class FirebaseException extends BaseException{

    public FirebaseException(FirebaseErrorCode errorCode) {
        super(errorCode);
    }
}
