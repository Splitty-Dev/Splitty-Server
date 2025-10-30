package com.chaegangjo.trade.service;

import com.chaegangjo.exception.TradeMemberException;
import com.chaegangjo.exception.errorcode.TradeMemberErrorCode;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.repository.TradeMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TradeMemberService {

    private final TradeMemberRepository tradeMemberRepository;

    public TradeMember findTradeMember(Long goods, Long memberId) {
        return tradeMemberRepository.findByGoods_IdAndMember_Id(goods, memberId)
                .orElseThrow(() -> new TradeMemberException(TradeMemberErrorCode.TRADE_MEMBER_NOT_FOUND));
    }

    public boolean existTradeMember(Long goodsId, Long memberId) {
        return tradeMemberRepository.existsByGoods_IdAndMember_Id(goodsId, memberId);
    }

    public List<TradeMember> findTradeMembersByGoodsId(Long goodsId) {
        return tradeMemberRepository.findAllByGoodsId(goodsId);
    }

    public List<TradeMember> findTradeMembersByMemberId(Long memberId) {
        return tradeMemberRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public TradeMember saveTradeMember(Goods goods, Member buyer, int quantity) {
        return tradeMemberRepository.save(new TradeMember(goods, buyer, quantity));
    }

    public boolean existsByGoodsAndMember(Goods goods, Member member) {
        return tradeMemberRepository.existsByGoodsAndMember(goods, member);
    }
}
