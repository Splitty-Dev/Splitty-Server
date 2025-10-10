package com.chaegangjo.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record GetWishList(
        @Schema(example = "10")
        Long cursorId,
        @Schema(example = "2025-10-10T14:51:24.999")
        LocalDateTime cursorCreatedAt
) {
}
