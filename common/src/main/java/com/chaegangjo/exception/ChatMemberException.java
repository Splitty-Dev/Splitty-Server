package com.chaegangjo.exception;

import com.chaegangjo.exception.errorcode.ChatMemberErrorCode;

public class ChatMemberException extends BaseException{

    public ChatMemberException(ChatMemberErrorCode errorCode) {
        super(errorCode);
    }
}