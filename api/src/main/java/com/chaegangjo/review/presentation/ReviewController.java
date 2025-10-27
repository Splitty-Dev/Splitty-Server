package com.chaegangjo.review.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.review.application.GetReviewsUsecase;
import com.chaegangjo.review.application.SaveReviewsUsecase;
import com.chaegangjo.review.dto.ReviewInfo;
import com.chaegangjo.review.dto.SaveReviewsRequest;
import com.chaegangjo.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final SaveReviewsUsecase saveReviewsUsecase;
    private final GetReviewsUsecase getReviewsUsecase;

    @Operation(summary = "리뷰 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveReviews(
            @RequestBody SaveReviewsRequest request,
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        saveReviewsUsecase.execute(request, user.getId());
        return ResponseEntity.ok(ApiResponse.success());
    }
}