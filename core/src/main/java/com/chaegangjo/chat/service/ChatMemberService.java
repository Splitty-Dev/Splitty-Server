package com.chaegangjo.chat.service;

import com.chaegangjo.exception.TradeMemberException;
import com.chaegangjo.exception.errorcode.TradeMemberErrorCode;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.repository.ChatMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatMemberService {

    private final ChatMemberRepository tradeMemberRepository;

    public ChatMember findChatMember(Long goods, Long memberId) {
        return tradeMemberRepository.findByGoods_IdAndMember_Id(goods, memberId)
                .orElseThrow(() -> new TradeMemberException(TradeMemberErrorCode.TRADE_MEMBER_NOT_FOUND));
    }

    public boolean existChatMember(Long goodsId, Long memberId) {
        return tradeMemberRepository.existsByGoods_IdAndMember_Id(goodsId, memberId);
    }

    public List<ChatMember> findChatMembersByGoodsId(Long goodsId) {
        return tradeMemberRepository.findAllByGoodsId(goodsId);
    }

    public List<ChatMember> findChatMembersByMemberId(Long memberId) {
        return tradeMemberRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public ChatMember saveChatMember(Goods goods, Member buyer, int quantity) {
        return tradeMemberRepository.save(new ChatMember(goods, buyer, quantity));
    }

    public boolean existsChatMemberByGoodsAndMember(Goods goods, Member member) {
        return tradeMemberRepository.existsByGoodsAndMember(goods, member);
    }
}
