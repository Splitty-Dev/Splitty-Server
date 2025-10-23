package com.chaegangjo.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SaveWishItemRequest (
        @Schema(example = "1") Long goodsId) {
}
