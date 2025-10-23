package com.chaegangjo.goods.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.QCategory;
import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.trade.domain.QTrade;
import com.chaegangjo.trade.domain.QTradeMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Slice<Goods> findPurchasedGoodsByCursor(IdCreatedAtCursorPage page, Long sellerId, TradeStatus status) {
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
    public Slice<Goods> findSoldGoodsByCursor(IdCreatedAtCursorPage page, Long buyerId, TradeStatus status) {
        QGoods goods = QGoods.goods;
        QTrade trade = QTrade.trade;
        QTradeMember tradeMember = QTradeMember.tradeMember;

        List<Goods> fetch = queryFactory.selectFrom(goods)
                .join(trade).on(trade.goods.eq(goods))
                .join(tradeMember).on(tradeMember.trade.eq(trade))
                .where(
                        eqBuyerId(buyerId, tradeMember),
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

    private BooleanExpression eqStatus(TradeStatus status, QGoods goods) {
        return (status != null) ? goods.status.eq(status) : null;
    }

    private BooleanExpression eqSellerId(Long sellerId, QGoods goods) {
        return (sellerId != null) ? goods.seller.id.eq(sellerId) : null;
    }

    private BooleanExpression eqBuyerId(Long buyerId, QTradeMember tradeMember) {
        return (buyerId != null) ? tradeMember.member.id.eq(buyerId) : null;
    }

    private BooleanExpression createdAtAndCursorId(LocalDateTime cursorCreatedAt, Long cursorId, QGoods goods) {
        return (cursorCreatedAt == null || cursorId == null) ?
                goods.createdAt.lt(cursorCreatedAt).or(goods.createdAt.eq(cursorCreatedAt).and(goods.id.lt(cursorId)))
                : null;
    }
}
