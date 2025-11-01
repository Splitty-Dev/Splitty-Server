package com.chaegangjo.exception;


import com.chaegangjo.exception.errorcode.SearchHistoryErrorCode;

public class SearchHistoryException extends BaseException{

    public SearchHistoryException(SearchHistoryErrorCode errorCode) {
        super(errorCode);
    }
}
