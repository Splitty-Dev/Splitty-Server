package com.chaegangjo.wishlist.repository;


import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.wishlist.domain.QWishList;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WishListCustomRepositoryImpl implements WishListCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<WishList> findAllByCursor(IdCreatedAtCursorPage page, Long memberId) {
        QWishList wishList = QWishList.wishList;
        QGoods goods = QGoods.goods;

        List<WishList> wishLists = queryFactory.selectFrom(wishList)
                .join(wishList.goods, goods).fetchJoin()
                .where(
                        eqMemberId(memberId, wishList),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), wishList)
                )
                .orderBy(wishList.createdAt.desc(), wishList.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = wishLists.size() > page.getSize();
        if (hasNext) wishLists.removeLast();

        return new SliceImpl<>(wishLists, PageRequest.of(0, page.getSize()), hasNext);
    }


    private BooleanExpression eqMemberId(Long memberId, QWishList wishList) {
        if (memberId == null) return null; //조건 적용X
        return wishList.member.id.eq(memberId);
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QWishList wishList) {
        if (cursorCreatedAt == null || cursorId == null) return null;
        return wishList.createdAt.lt(cursorCreatedAt)
                .or(wishList.createdAt.eq(cursorCreatedAt)
                        .and(wishList.id.lt(cursorId)));
    }
}
