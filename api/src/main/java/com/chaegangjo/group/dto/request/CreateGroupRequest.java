package com.chaegangjo.group.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateGroupRequest(
        @Schema(description = "그룹명", example = "우리 동네 장보기 모임")
        String name,

        @Schema(description = "그룹 설명", example = "매주 금요일 함께 장보는 모임입니다.")
        String description,

        @Schema(description = "최대 인원 수", example = "10")
        int maxMemberCount
) {
}
