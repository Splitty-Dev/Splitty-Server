package com.chaegangjo.paging;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IdCreatedAtNextCursor extends NextCursor{

    @Schema(example = "2025-10-10T14:51:24.999")
    LocalDateTime lastCreatedAt;

    public IdCreatedAtNextCursor(Long lastId, LocalDateTime lastCreatedAt) {
        super(lastId);
        this.lastCreatedAt = lastCreatedAt;
    }
}
