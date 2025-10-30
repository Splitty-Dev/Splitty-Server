package com.chaegangjo.trade.service;

import com.chaegangjo.exception.TradeMemberException;
import com.chaegangjo.exception.errorcode.TradeMemberErrorCode;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.trade.domain.Trade;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.repository.TradeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TradeMemberService {

    private final TradeMemberRepository tradeMemberRepository;

    public TradeMember findTradeMember(Long tradeId, Long memberId) {
        return tradeMemberRepository.findByTrade_IdAndMember_Id(tradeId, memberId)
                .orElseThrow(() -> new TradeMemberException(TradeMemberErrorCode.TRADE_MEMBER_NOT_FOUND));
    }

    public boolean existTradeMember(Long goodsId, Long memberId) {
        return tradeMemberRepository.existsByTrade_Goods_IdAndMember_Id(goodsId, memberId);
    }

    public List<TradeMember> findTradeMembersByTradeId(Long tradeId) {
        return tradeMemberRepository.findAllByTradeId(tradeId);
    }

    public List<TradeMember> findTradeMembersByMemberId(Long memberId) {
        return tradeMemberRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public TradeMember saveTradeMember(Trade trade, Member buyer, int quantity) {
        return tradeMemberRepository.save(new TradeMember(trade, buyer, quantity));
    }

    public boolean existsByTradeAndMember(Trade trade, Member member) {
        return tradeMemberRepository.existsByTradeAndMember(trade, member);
    }
}
