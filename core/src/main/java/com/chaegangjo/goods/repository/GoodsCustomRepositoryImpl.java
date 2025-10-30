package com.chaegangjo.goods.repository;

import com.chaegangjo.chat.domain.QChatMember;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.QCategory;
import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GoodsCustomRepositoryImpl implements GoodsCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Goods> findGoodsWithDetail(Long id) {
        QGoods goods = QGoods.goods;
        QMember seller = QMember.member;
        QCategory category = QCategory.category;

        return Optional.ofNullable(
                queryFactory.selectFrom(goods)
                        .join(goods.seller, seller).fetchJoin()
                        .join(goods.category, category).fetchJoin()
                        .where(goods.id.eq(id))
                        .fetchOne()
        );
    }

    @Override
    public Slice<Goods> findAllPurchasedByCursor(IdCreatedAtCursorPage page, Long sellerId, TradeStatus status) {
        QMember member = QMember.member;
        QGoods goods = QGoods.goods;

        List<Goods> fetch = queryFactory.selectFrom(goods)
                .join(goods.seller, member).fetchJoin()
                .where(
                        eqSellerId(sellerId, goods),
                        eqStatus(status, goods),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), goods)
                )
                .orderBy(goods.createdAt.desc(), goods.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = fetch.size() > page.getSize();
        if (hasNext) fetch.getLast();

        return new SliceImpl<>(fetch, PageRequest.of(0, page.getSize()), hasNext);
    }

    @Override
    public Slice<Goods> findAllByCursor(CursorPage page, List<Long> goodsIds, Long categoryId) {
        QGoods goods = QGoods.goods;

        List<Goods> fetch = queryFactory.selectFrom(goods)
                .where(
                        inGoodsIds(goodsIds, goods),
                        eqCategory(categoryId, goods),
                        cursorId(page.getCursorId(), goods)
                )
                .orderBy(goods.createdAt.desc(), goods.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = fetch.size() > page.getSize();
        if (hasNext) fetch.getLast();

        return new SliceImpl<>(fetch, PageRequest.of(0, page.getSize()), hasNext);
    }

    @Override
    public Slice<Goods> findAllByKeywordAndCursor(IdCreatedAtCursorPage page, String keyword) {
        QGoods goods = QGoods.goods;

        List<Goods> fetch = queryFactory.selectFrom(goods)
                .where(
                        containsKeyword(keyword, goods),
                        cursorId(page.getCursorId(), goods)
                )
                .orderBy(goods.createdAt.desc(), goods.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = fetch.size() > page.getSize();
        if (hasNext) fetch.getLast();

        return new SliceImpl<>(fetch, PageRequest.of(0, page.getSize()), hasNext);
    }

    private static BooleanExpression inGoodsIds(List<Long> goodsIds, QGoods goods) {
        if (goodsIds == null || goodsIds.isEmpty()) {
            return Expressions.FALSE;
        }
        return goods.id.in(goodsIds);
    }

    private BooleanExpression containsKeyword(String keyword, QGoods goods) {
        if (keyword == null || keyword.isBlank()) {
            return Expressions.FALSE;
        }
        return goods.name.containsIgnoreCase(keyword)
                .or(goods.description.containsIgnoreCase(keyword));
    }

    @Override
    public Slice<Goods> findAllSoldByCursor(IdCreatedAtCursorPage page, Long buyerId, TradeStatus status) {
        QGoods goods = QGoods.goods;
        QChatMember chatMember = QChatMember.chatMember;

        List<Goods> fetch = queryFactory.selectFrom(goods)
                .join(chatMember).on(chatMember.goods.eq(goods))
                .where(
                        eqBuyerId(buyerId, chatMember),
                        eqStatus(status, goods),
                        createdAtAndCursorId(page.getCursorCreatedAt(), page.getCursorId(), goods)
                )
                .orderBy(goods.createdAt.desc(), goods.id.desc())
                .limit(page.getSize() + 1)
                .fetch();

        boolean hasNext = fetch.size() > page.getSize();
        if (hasNext) fetch.getLast();

        return new SliceImpl<>(fetch, PageRequest.of(0, page.getSize()), hasNext);
    }

    private static BooleanExpression eqCategory(Long categoryId, QGoods goods) {
        return (categoryId != null && categoryId != 0) ? goods.category.id.eq(categoryId) : null;
    }

    private BooleanExpression eqStatus(TradeStatus status, QGoods goods) {
        return (status != null) ? goods.status.eq(status) : null;
    }

    private BooleanExpression eqSellerId(Long sellerId, QGoods goods) {
        return (sellerId != null) ? goods.seller.id.eq(sellerId) : null;
    }

    private BooleanExpression eqBuyerId(Long buyerId, QChatMember chatMember) {
        return (buyerId != null) ? chatMember.member.id.eq(buyerId) : null;
    }

    private BooleanExpression cursorId(Long cursorId, QGoods goods) {
        return (cursorId != null) ? goods.id.lt(cursorId) : null;
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QGoods goods) {
        return (cursorCreatedAt != null && cursorId != null) ?
                goods.createdAt.lt(cursorCreatedAt).or(goods.createdAt.eq(cursorCreatedAt).and(goods.id.lt(cursorId)))
                : null;
    }
}
