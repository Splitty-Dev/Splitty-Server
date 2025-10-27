package com.chaegangjo.review.repository;


import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.review.domain.QReview;
import com.chaegangjo.review.domain.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Review> findAllByCursor(IdCreatedAtCursorPage page, Long revieweeId) {
        QReview review = QReview.review;

        List<Review> reviews = queryFactory.selectFrom(review)
                .where(
                        eqMemberId(revieweeId, review),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), review)
                )
                .orderBy(review.createdAt.desc(), review.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = reviews.size() > page.getSize();
        if (hasNext) reviews.removeLast();

        return new SliceImpl<>(reviews, PageRequest.of(0, page.getSize()), hasNext);
    }


    private BooleanExpression eqMemberId(Long revieweeId, QReview review) {
        if (revieweeId == null) return null; //조건 적용X
        return review.reviewee.id.eq(revieweeId);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QReview review) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return review.createdAt.lt(cursorCreatedAt)
                .or(review.createdAt.eq(cursorCreatedAt)
                        .and(review.id.lt(cursorId)));
    }
}
