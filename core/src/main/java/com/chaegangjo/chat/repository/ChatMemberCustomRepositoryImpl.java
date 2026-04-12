package com.chaegangjo.chat.repository;

import com.chaegangjo.chat.domain.QChatMember;
import com.chaegangjo.chat.enums.TradeRole;
import com.chaegangjo.goods.domain.QGoods;
import com.chaegangjo.member.domain.QMember;
import com.chaegangjo.chat.domain.ChatMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatMemberCustomRepositoryImpl implements ChatMemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatMember> findAllByGoodsId(Long goodsId) {
        QMember member = QMember.member;
        QChatMember chatMember = QChatMember.chatMember;

        return queryFactory.selectFrom(chatMember)
                .join(chatMember.member, member).fetchJoin()
                .where(eqGoodsId(goodsId, chatMember))
                .orderBy(chatMember.member.id.asc())
                .fetch();
    }

    @Override
    public List<ChatMember> findActiveAllByGoodsId(Long goodsId) {
        QMember member = QMember.member;
        QChatMember chatMember = QChatMember.chatMember;

        return queryFactory.selectFrom(chatMember)
                .join(chatMember.member, member).fetchJoin()
                .where(eqGoodsId(goodsId, chatMember)
                , chatMember.active.isTrue())
                .orderBy(chatMember.member.id.asc())
                .fetch();
    }

    @Override
    public List<ChatMember> findActiveAllByMemberId(Long memberId) {
        QMember member = QMember.member;
        QGoods goods = QGoods.goods;
        QChatMember chatMember = QChatMember.chatMember;

        return queryFactory.selectFrom(chatMember)
                .join(chatMember.member, member).fetchJoin()
                .join(chatMember.goods, goods).fetchJoin()
                .where(eqMemberId(memberId, chatMember),
                        chatMember.active.isTrue())
                .orderBy(chatMember.goods.id.desc())
                .fetch();
    }

    @Override
    public List<ChatMember> findActiveAllByMemberIdAndTradeRole(Long memberId, TradeRole role) {
        QMember member = QMember.member;
        QGoods goods = QGoods.goods;
        QChatMember chatMember = QChatMember.chatMember;

        return queryFactory.selectFrom(chatMember)
                .join(chatMember.member, member).fetchJoin()
                .join(chatMember.goods, goods).fetchJoin()
                .where(eqMemberId(memberId, chatMember),
                        chatMember.active.isTrue(),
                        eqTradeRole(role, chatMember))
                .orderBy(chatMember.goods.id.desc())
                .fetch();
    }

    private BooleanExpression eqGoodsId(Long goodsId, QChatMember chatMember) {
        if (goodsId == null) return null;
        return chatMember.goods.id.eq(goodsId);
    }

    private BooleanExpression eqMemberId(Long memberId, QChatMember chatMember) {
        if (memberId == null) return null;
        return chatMember.member.id.eq(memberId);
    }

    private BooleanExpression eqTradeRole(TradeRole role, QChatMember chatMember) {
        if (role == null) return null;
        return chatMember.tradeRole.eq(role);
    }
}
