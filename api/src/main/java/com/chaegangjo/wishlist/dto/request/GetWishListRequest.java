package com.chaegangjo.wishlist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record GetWishListRequest(
        @Schema(example = "20")
        Long cursorId,
        @Schema(example = "2025-10-12T14:51:24.999")
        LocalDateTime cursorCreatedAt
) {
}
