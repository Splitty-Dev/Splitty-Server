package com.chaegangjo.trade.repository;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.trade.domain.QTradeMember;
import com.chaegangjo.trade.domain.TradeMember;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    private BooleanExpression eqTradeId(Long tradeId, QTradeMember tradeMember) {
        if (tradeId == null) return null;
        return tradeMember.trade.id.eq(tradeId);
    }
}
