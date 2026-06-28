package com.chaegangjo.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterKeywordRequest(
        @Schema(example = "생수")
        String keyword) {
}
