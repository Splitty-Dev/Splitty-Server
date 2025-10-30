package com.chaegangjo.chat.repository;

import com.chaegangjo.chat.domain.ChatMember;

import java.util.List;

public interface ChatMemberCustomRepository {

    List<ChatMember> findAllByGoodsId(Long goodsId);
    List<ChatMember> findAllByMemberId(Long memberId);
}