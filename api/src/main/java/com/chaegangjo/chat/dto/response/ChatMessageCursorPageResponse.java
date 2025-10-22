package com.chaegangjo.chat.dto.response;

import com.chaegangjo.dto.CursorPageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessageCursorPageResponse<T> extends CursorPageResponse<T> {

    NextCursor nextCursor;

    @Builder
    public ChatMessageCursorPageResponse(T data, boolean hasNext, NextCursor nextCursor) {
        super(data, hasNext);
        this.nextCursor = nextCursor;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class NextCursor {

        @Schema(example = "10")
        Long lastId;
        @Schema(example = "2025-10-10T14:51:24.999")
        LocalDateTime lastCreatedAt;
    }
}
