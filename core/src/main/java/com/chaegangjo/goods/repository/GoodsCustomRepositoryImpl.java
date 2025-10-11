package com.chaegangjo.goods.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.QCategory;
import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
