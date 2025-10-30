package com.chaegangjo.trade.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.trade.domain.TradeMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeMemberRepository extends JpaRepository<TradeMember, Long>, TradeMemberCustomRepository {

    Optional<TradeMember> findByGoods_IdAndMember_Id(Long goodsId, Long memberId);
    List<TradeMember> findAllByMember_Id(Long memberId);
    boolean existsByGoods_IdAndMember_Id(Long goodsId, Long memberId);
    boolean existsByGoodsAndMember(Goods goods, Member member);
}

