package com.chaegangjo.group.dto;

import com.chaegangjo.group.domain.GroupTrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupTradeInfo {

    @Schema(example = "1")
    private Long id;

    @Schema(description = "거래명", example = "2024년 12월 장보기")
    private String name;

    @Schema(description = "인당 가격 (원)", example = "8500")
    private int pricePerPerson;

    @Schema(description = "등록 날짜")
    private LocalDateTime createdAt;

    public GroupTradeInfo(Long id, String name, int pricePerPerson, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.pricePerPerson = pricePerPerson;
        this.createdAt = createdAt;
    }

    public static GroupTradeInfo from(GroupTrade groupTrade) {
        return new GroupTradeInfo(
                groupTrade.getId(),
                groupTrade.getName(),
                groupTrade.getPricePerPerson(),
                groupTrade.getCreatedAt()
        );
    }
}
