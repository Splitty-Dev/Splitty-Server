package com.chaegangjo.review.repository;


import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.review.domain.Review;
import org.springframework.data.domain.Slice;

public interface ReviewCustomRepository {

    Slice<Review> findAllByCursor(IdCreatedAtCursorPage page, Long revieweeId);
}