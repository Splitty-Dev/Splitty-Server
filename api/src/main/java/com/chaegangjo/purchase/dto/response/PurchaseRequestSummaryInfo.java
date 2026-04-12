package com.chaegangjo.purchase.dto.response;

import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.enums.PurchaseRequestStatus;
import com.chaegangjo.utils.S3Utils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PurchaseRequestSummaryInfo(
        @Schema(example = "1")
        Long id,
        @Schema(example = "아이폰 케이블 대신 사다 주실 분 구해요")
        String title,
        @Schema(example = "https://samak-bucket.s3.ap-northeast-2.amazonaws.com/")
        String imageUrlPrefix,
        @Schema(example = "purchase-request-image.jpg")
        String imageName,
        @Schema(example = "1000")
        int fee,
        @Schema(example = "2026-04-10T18:00:00")
        LocalDateTime deadline,
        @Schema(example = "강남역 2번 출구")
        String preferredLocation,
        @Schema(example = "OPEN", description = "OPEN: 모집중, CLOSED: 모집완료")
        PurchaseRequestStatus status
) {

    public static PurchaseRequestSummaryInfo from(PurchaseRequest request) {
        return new PurchaseRequestSummaryInfo(
                request.getId(),
                request.getTitle(),
                S3Utils.S3_BUCKET_URL_PREFIX,
                request.getImageName(),
                request.getFee(),
                request.getDeadline(),
                request.getPreferredLocation(),
                request.getStatus()
        );
    }
}
