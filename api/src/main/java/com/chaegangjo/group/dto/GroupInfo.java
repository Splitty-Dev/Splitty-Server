package com.chaegangjo.group.dto;

import com.chaegangjo.group.domain.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInfo {

    @Schema(example = "1")
    private Long id;

    @Schema(description = "그룹명", example = "우리 동네 장보기 모임")
    private String name;

    @Schema(description = "그룹 설명", example = "매주 금요일 함께 장보는 모임입니다.")
    private String description;

    @Schema(description = "그룹 참여 코드", example = "AB12CD34")
    private String joinCode;

    @Schema(description = "호스트 멤버 ID", example = "5")
    private Long hostId;

    @Schema(description = "최대 인원 수", example = "10")
    private int maxMemberCount;

    @Schema(description = "현재 인원 수", example = "3")
    private int currentMemberCount;

    public GroupInfo(Long id, String name, String description, String joinCode, Long hostId, int maxMemberCount, int currentMemberCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.joinCode = joinCode;
        this.hostId = hostId;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = currentMemberCount;
    }

    public static GroupInfo from(Group group) {
        return new GroupInfo(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getJoinCode(),
                group.getHost().getId(),
                group.getMaxMemberCount(),
                group.getCurrentMemberCount()
        );
    }
}
