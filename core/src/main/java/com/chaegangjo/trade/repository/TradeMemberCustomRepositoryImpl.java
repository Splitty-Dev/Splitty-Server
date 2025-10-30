package com.chaegangjo.trade.repository;

import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.trade.domain.QTrade;
import com.chaegangjo.trade.domain.QTradeMember;
import com.chaegangjo.trade.domain.TradeMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TradeMemberCustomRepositoryImpl implements TradeMemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TradeMember> findAllByTradeId(Long tradeId) {
        QMember member = QMember.member;
        QTradeMember tradeMember = QTradeMember.tradeMember;

        return queryFactory.selectFrom(tradeMember)
                .join(tradeMember.member, member).fetchJoin()
                .where(eqTradeId(tradeId, tradeMember))
                .orderBy(tradeMember.member.id.asc())
                .fetch();
    }

    @Override
    public List<TradeMember> findAllByMemberId(Long memberId) {
        QMember member = QMember.member;
        QTrade trade = QTrade.trade;
        QGoods goods = QGoods.goods;
        QTradeMember tradeMember = QTradeMember.tradeMember;

        return queryFactory.selectFrom(tradeMember)
                .join(tradeMember.member, member).fetchJoin()
                .join(tradeMember.trade, trade).fetchJoin()
                .join(trade.goods, goods).fetchJoin()
                .where(eqMemberId(memberId, tradeMember))
                .orderBy(tradeMember.trade.id.desc())
                .fetch();
    }

    private BooleanExpression eqTradeId(Long tradeId, QTradeMember tradeMember) {
        if (tradeId == null) return null;
        return tradeMember.trade.id.eq(tradeId);
    }

    private BooleanExpression eqMemberId(Long memberId, QTradeMember tradeMember) {
        if (memberId == null) return null;
        return tradeMember.member.id.eq(memberId);
    }
}
