package com.chaegangjo.member.repository;


import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long>, WishListCustomRepository {

    Boolean existsByMemberAndGoods(Member member, Goods goods);
}