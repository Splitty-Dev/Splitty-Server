package com.chaegangjo.member.dto;

import com.chaegangjo.member.domain.SearchHistory;
import io.swagger.v3.oas.annotations.media.Schema;

public record SearchHistoryInfo (
        @Schema(example = "1")
        Long id,
        @Schema(example = "생수")
        String keyword
) {
    public static SearchHistoryInfo from(SearchHistory searchHistory) {
        return new SearchHistoryInfo(searchHistory.getId(), searchHistory.getKeyword());
    }
}
