package com.chaegangjo.chat.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.chat.domain.ChatMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long>, ChatMemberCustomRepository {

    Optional<ChatMember> findByGoods_IdAndMember_Id(Long goodsId, Long memberId);
    List<ChatMember> findAllByMember_Id(Long memberId);
    boolean existsByGoods_IdAndMember_Id(Long goodsId, Long memberId);
    boolean existsByGoodsAndMember(Goods goods, Member member);
}

