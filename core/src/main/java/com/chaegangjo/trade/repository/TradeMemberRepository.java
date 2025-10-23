package com.chaegangjo.trade.repository;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.trade.domain.Trade;
import com.chaegangjo.trade.domain.TradeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeMemberRepository extends JpaRepository<TradeMember, Long>, TradeMemberCustomRepository {

    Optional<TradeMember> findByTrade_IdAndMember_Id(Long tradeId, Long memberId);

    boolean existsByTradeAndMember(Trade trade, Member member);
}
