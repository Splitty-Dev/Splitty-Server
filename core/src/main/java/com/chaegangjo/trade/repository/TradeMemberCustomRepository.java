package com.chaegangjo.trade.repository;

import com.chaegangjo.trade.domain.TradeMember;

import java.util.List;

public interface TradeMemberCustomRepository {

    List<TradeMember> findAllByTradeId(Long tradeId);
}