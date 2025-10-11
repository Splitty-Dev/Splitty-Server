package com.chaegangjo.wishlist.repository;


import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.wishlist.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long>, WishListCustomRepository {

    boolean existsByMemberAndGoods(Member member, Goods goods);

    Optional<WishList> findWishListByMemberAndGoods(Member member, Goods goods);
}