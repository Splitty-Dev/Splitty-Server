package com.chaegangjo.chat.repository;

import com.chaegangjo.chat.domain.ChatMember;

import com.chaegangjo.chat.enums.TradeRole;
import java.util.List;

public interface ChatMemberCustomRepository {

    List<ChatMember> findAllByGoodsId(Long goodsId);
    List<ChatMember> findActiveAllByGoodsId(Long goodsId);
    List<ChatMember> findActiveAllByMemberId(Long memberId);
    List<ChatMember> findActiveAllByMemberIdAndTradeRole(Long memberId, TradeRole role);
}