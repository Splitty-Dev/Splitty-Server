package com.chaegangjo.group.dto;

import com.chaegangjo.group.domain.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupListInfo {

    @Schema(example = "1")
    private Long id;

    @Schema(description = "그룹명", example = "우리 동네 장보기 모임")
    private String name;

    @Schema(description = "그룹 설명", example = "매주 금요일 함께 장보는 모임입니다.")
    private String description;

    @Schema(description = "호스트 멤버 ID", example = "5")
    private Long hostId;

    @Schema(description = "최대 인원 수", example = "10")
    private int maxMemberCount;

    @Schema(description = "현재 인원 수", example = "4")
    private int currentMemberCount;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    public GroupListInfo(Long id, String name, String description, Long hostId, int maxMemberCount, int currentMemberCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hostId = hostId;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = currentMemberCount;
        this.createdAt = createdAt;
    }

    public static GroupListInfo from(Group group) {
        return new GroupListInfo(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getHost().getId(),
                group.getMaxMemberCount(),
                group.getCurrentMemberCount(),
                group.getCreatedAt()
        );
    }
}
