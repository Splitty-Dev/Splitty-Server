package com.chaegangjo.review.repository;

import com.chaegangjo.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
}
