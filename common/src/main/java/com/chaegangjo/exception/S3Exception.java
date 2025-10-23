package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.S3ErrorCode;

public class S3Exception extends BaseException {

    public S3Exception(S3ErrorCode errorCode) { super(errorCode);}
}
