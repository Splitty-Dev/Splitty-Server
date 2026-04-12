package com.chaegangjo.purchase.dto.response;

import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.enums.PurchaseRequestStatus;
import com.chaegangjo.utils.S3Utils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PurchaseRequestInfo(
        @Schema(example = "1")
        Long id,
        @Schema(example = "1")
        Long requesterId,
        @Schema(example = "재밌는원숭이31")
        String requesterUsername,
        @Schema(example = "https://profile-image.png")
        String requesterProfileImageUrl,
        @Schema(example = "공릉동")
        String requesterNeighName,
        @Schema(example = "4.5")
        float requesterRating,
        @Schema(example = "12")
        int requesterReviewCount,
        @Schema(example = "아이폰 케이블 대신 사다 주실 분 구해요")
        String title,
        @Schema(example = "https://samak-bucket.s3.ap-northeast-2.amazonaws.com/")
        String imageUrlPrefix,
        @Schema(example = "purchase-request-image.jpg")
        String imageName,
        @Schema(example = "2026-04-10T18:00:00")
        LocalDateTime deadline,
        @Schema(example = "강남역 2번 출구")
        String preferredLocation,
        @Schema(example = "1000")
        int fee,
        @Schema(example = "마트에서 아이폰 케이블 하나만 사다 주시면 감사하겠습니다.")
        String content,
        @Schema(example = "42")
        int viewCount,
        @Schema(example = "OPEN", description = "OPEN: 모집중, CLOSED: 모집완료")
        PurchaseRequestStatus status,
        @Schema(example = "2026-04-07T10:00:00")
        LocalDateTime createdAt
) {

    public static PurchaseRequestInfo from(PurchaseRequest request) {
        return new PurchaseRequestInfo(
                request.getId(),
                request.getRequester().getId(),
                request.getRequester().getUsername(),
                request.getRequester().getProfileImageUrl(),
                request.getRequester().getNeighName(),
                request.getRequester().getRating(),
                request.getRequester().getReviewCount(),
                request.getTitle(),
                S3Utils.S3_BUCKET_URL_PREFIX,
                request.getImageName(),
                request.getDeadline(),
                request.getPreferredLocation(),
                request.getFee(),
                request.getContent(),
                request.getViewCount(),
                request.getStatus(),
                request.getCreatedAt()
        );
    }
}
