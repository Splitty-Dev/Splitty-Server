package com.chaegangjo.chat.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class GetChatMessagesUseCaseTest {

    @Autowired
    GetChatMessagesUseCase getChatMessagesUseCase;

    @DisplayName("채팅 메시지 조회 성능 테스트 - Offset Pagination")
    @Test
    void getByOffset() {
        long startTime = System.currentTimeMillis();
        getChatMessagesUseCase.executeByOffset(9L, 1999_800L);
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time (Offset): " + (endTime - startTime) + " ms");
    }

    @DisplayName("채팅 메시지 조회 성능 테스트 - Cursor Pagination")
    @Test
    void getByCursor() {
        long startTime = System.currentTimeMillis();
        getChatMessagesUseCase.execute(9L, 500L, LocalDateTime.parse("2025-11-13T10:18:54.000000"));
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time (Cursor): " + (endTime - startTime) + " ms");
    }
}