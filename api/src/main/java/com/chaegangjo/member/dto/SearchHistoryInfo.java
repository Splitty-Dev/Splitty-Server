package com.chaegangjo.member.prensentation;

import io.swagger.v3.oas.annotations.media.Schema;

public record SearchHistoryInfo (
        @Schema(example = "1")
        Long id,
        @Schema(example = "생수")
        String keyword
) {
    
}
