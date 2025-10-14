package com.chaegangjo.trade.service;

import com.chaegangjo.exception.TradeMemberException;
import com.chaegangjo.exception.errorcode.TradeMemberErrorCode;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.repository.TradeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TradeMemberService {

    private final TradeMemberRepository tradeMemberRepository;

    public TradeMember findTradeMember(Long tradeId, Long memberId) {
        return tradeMemberRepository.findByTrade_IdAndMember_Id(tradeId, memberId)
                .orElseThrow(() -> new TradeMemberException(TradeMemberErrorCode.TRADEMEMBER_NOT_FOUND));
    }
}
