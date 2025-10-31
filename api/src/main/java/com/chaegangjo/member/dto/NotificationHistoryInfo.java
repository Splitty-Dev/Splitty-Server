package com.chaegangjo.member.dto;

import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.domain.NotificationHistory;
import com.chaegangjo.utils.S3Utils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record NotificationHistoryInfo(
        @Schema(example = "1")
        Long id,
        @Schema(example = "거래가 완료되었어요.")
        String title,
        @Schema(example = "거래가 성공적으로 완료되었어요. 후기를 남겨보세요!")
        String body,
        @Schema(example = "https://bucket.amazonaws.com/")
        String imageUrlPrefix,
        @Schema(example = "image1.jpg")
        String imageName,
        @Schema(example = "2025-05-01T12:34:56")
        LocalDateTime createdAt
) {

    public static NotificationHistoryInfo from(NotificationHistory notificationHistory) {
        Notification notification = notificationHistory.getNotification();
        return new NotificationHistoryInfo(
                notification.getId(),
                notification.getTitle(),
                notification.getBody(),
                S3Utils.S3_BUCKET_URL_PREFIX,
                notification.getImageName(),
                notification.getCreatedAt()
        );
    }
}