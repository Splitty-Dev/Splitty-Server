package com.chaegangjo.member.dto;

import com.chaegangjo.member.domain.KeywordNotification;
import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordNotificationInfo(
        @Schema(example = "1")
        Long id,
        @Schema(example = "생수")
        String keyword
) {
    public static KeywordNotificationInfo from(KeywordNotification keywordNotification) {
        return new KeywordNotificationInfo(keywordNotification.getId(), keywordNotification.getKeyword());
    }
}
