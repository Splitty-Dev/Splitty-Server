package com.chaegangjo.chat.service;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.enums.TradeRole;
import com.chaegangjo.chat.repository.ChatMemberRepository;
import com.chaegangjo.exception.ChatMemberException;
import com.chaegangjo.exception.errorcode.ChatMemberErrorCode;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatMemberService {

    private final ChatMemberRepository tradeMemberRepository;

    public ChatMember findChatMemberByGoodsIdAndMemberId(Long goodsId, Long memberId) {
        return tradeMemberRepository.findByGoods_IdAndMember_Id(goodsId, memberId)
                .orElseThrow(() -> new ChatMemberException(ChatMemberErrorCode.TRADE_MEMBER_NOT_FOUND));
    }

    public ChatMember findChatMemberByGoodsAndMember(Goods goods, Member member) {
        return tradeMemberRepository.findByGoodsAndMember(goods, member)
                .orElseThrow(() -> new ChatMemberException(ChatMemberErrorCode.TRADE_MEMBER_NOT_FOUND));
    }

    public List<ChatMember> findAllByGoodsId(Long goodsId) {
        return tradeMemberRepository.findAllByGoodsId(goodsId);
    }

    public List<ChatMember> findActiveAllByGoodsId(Long goodsId) {
        return tradeMemberRepository.findAllByGoodsId(goodsId);
    }

    public List<ChatMember> findActiveAllByMemberId(Long memberId) {
        return tradeMemberRepository.findActiveAllByMemberId(memberId);
    }

    public List<ChatMember> findActiveAllByMemberIdAndTradeRole(Long memberId, TradeRole role) {
        return tradeMemberRepository.findActiveAllByMemberIdAndTradeRole(memberId, role);
    }

    public boolean existChatMember(Long goodsId, Long memberId) {
        return tradeMemberRepository.existsByGoods_IdAndMember_Id(goodsId, memberId);
    }

    public boolean existActiveChatMember(Long goodsId, Long memberId) {
        return tradeMemberRepository.existsByGoods_IdAndMember_IdAndActiveIsTrue(goodsId, memberId);
    }

    @Transactional
    public ChatMember saveChatMember(Goods goods, Member buyer, int quantity, TradeRole role) {
        return tradeMemberRepository.save(new ChatMember(goods, buyer, quantity, role));
    }

    public boolean existsChatMemberByGoodsAndMember(Goods goods, Member member) {
        return tradeMemberRepository.existsByGoodsAndMember(goods, member);
    }

    public void deactiveChatMember(Long memberId, Long goodsId) {
        ChatMember chatMember = findChatMemberByGoodsIdAndMemberId(goodsId, memberId);
        chatMember.deactivate();
    }
}
