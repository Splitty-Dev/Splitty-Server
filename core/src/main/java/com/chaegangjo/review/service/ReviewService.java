package com.chaegangjo.review.service;

import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.review.domain.Review;
import com.chaegangjo.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void saveReviews(List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    public Slice<Review> findAllByCursor(IdCreatedAtCursorPage cursorPage, Long revieweeId) {
        return reviewRepository.findAllByCursor(cursorPage, revieweeId);
    }
}