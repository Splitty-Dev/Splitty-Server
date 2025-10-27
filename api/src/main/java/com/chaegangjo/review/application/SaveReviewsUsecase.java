package com.chaegangjo.review.application;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.review.domain.Review;
import com.chaegangjo.review.dto.SaveReviewsRequest;
import com.chaegangjo.review.service.ReviewService;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.service.TradeMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class SaveReviewsUsecase {

    private final MemberService memberService;
    private final TradeMemberService tradeMemberService;
    private final ReviewService reviewService;

    @Transactional
    public void execute(SaveReviewsRequest request, Long reviewerId) {
        TradeMember reviewer = tradeMemberService.findTradeMember(request.getTradeId(), reviewerId);
        //TODO: 리뷰 작성 권한 검증, 중복 작성 방지, 자기 자신 리뷰 작성 방지, 참여 회원 여부 검증
        List<Review> reviews = request.getReviews().stream()
                        .map(review -> {
                            Member reviewee = memberService.findMemberById(review.getRevieweeId());
                            reviewee.calculateRating(review.getRating());
                            return Review.builder()
                                    .reviewer(reviewer)
                                    .reviewee(reviewee)
                                    .rating(review.getRating())
                                    .content(review.getContent())
                                    .build();
                        }).toList();
        reviewService.saveReviews(reviews);
    }
}
