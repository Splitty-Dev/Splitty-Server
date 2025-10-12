package com.chaegangjo.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getStatus();
    String getValue();
    String getMessage();
}