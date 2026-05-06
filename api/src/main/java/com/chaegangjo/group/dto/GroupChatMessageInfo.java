package com.chaegangjo.group.dto;

import com.chaegangjo.group.domain.GroupChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record GroupChatMessageInfo(
        @Schema(example = "1")
        Long id,

        @Schema(description = "보낸 사람 멤버 ID", example = "5")
        Long senderId,

        @Schema(example = "생수 같이 사실 분 계신가요?")
        String message,

        @Schema(example = "2024-12-06T14:30:00")
        LocalDateTime createdAt
) {
    public static GroupChatMessageInfo from(GroupChatMessage chatMessage) {
        return new GroupChatMessageInfo(
                chatMessage.getId(),
                chatMessage.getGroupChatMember().getMember().getId(),
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }
}
