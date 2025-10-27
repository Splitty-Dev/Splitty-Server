package com.chaegangjo.review.application;

import static com.chaegangjo.paging.PageProperties.REVIEW_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import com.chaegangjo.review.domain.Review;
import com.chaegangjo.review.dto.ReviewInfo;
import com.chaegangjo.review.service.ReviewService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetReviewsUsecase {

    private final ReviewService reviewService;

    public CursorPageResponse<List<ReviewInfo>> execute(Long cursorId, LocalDateTime createdAt, Long revieweeId) {
        Slice<Review> reviews = reviewService.getReviews(new IdCreatedAtCursorPage(REVIEW_PAGE_SIZE, cursorId, createdAt), revieweeId);
        List<Review> content = reviews.getContent();
        IdCreatedAtNextCursor nextCursor = null;
        if (reviews.hasNext()) {
            Review last = content.getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        List<ReviewInfo> data = content.stream()
                .map(ReviewInfo::from)
                .toList();

        return CursorPageResponse.<List<ReviewInfo>>builder()
                .data(data)
                .hasNext(reviews.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
