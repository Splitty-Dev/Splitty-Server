package com.chaegangjo.group.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record JoinGroupRequest(
        @Schema(description = "그룹 참여 코드 (8자리)", example = "AB12CD34")
        String joinCode
) {
}
