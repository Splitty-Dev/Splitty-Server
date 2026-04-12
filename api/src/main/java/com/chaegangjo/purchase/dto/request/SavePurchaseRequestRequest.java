package com.chaegangjo.purchase.dto.request;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record SavePurchaseRequestRequest(
        @Schema(example = "아이폰 케이블 대신 사다 주실 분 구해요")
        String title,
        @Schema(example = "2026-04-10T18:00:00", description = "요청 마감 시간")
        LocalDateTime deadline,
        @Schema(example = "강남역 2번 출구")
        String preferredLocation,
        @Schema(example = "1000", description = "수수료 (원)")
        int fee,
        @Schema(example = "마트에서 아이폰 케이블 하나만 사다 주시면 감사하겠습니다.")
        String content,
        @Schema(example = "purchase-request-image.jpg", description = "S3에 업로드된 이미지 파일명")
        String imageName
) {

    public PurchaseRequest toEntity(Member requester) {
        return PurchaseRequest.builder()
                .requester(requester)
                .title(title)
                .deadline(deadline)
                .preferredLocation(preferredLocation)
                .fee(fee)
                .content(content)
                .imageName(imageName)
                .build();
    }
}
