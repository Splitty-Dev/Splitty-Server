package com.chaegangjo.logger;

public interface UserActionLogger {
    void logAction(Long userId, UserAction action, Long itemId);
    void flush();
}
