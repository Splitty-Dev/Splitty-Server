package com.chaegangjo.wishlist.application;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.wishlist.presentation.ExistsInWishListResponse;
import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExistsInWishListUsecase {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final WishListService wishListService;

    public ExistsInWishListResponse execute(Long memberId, Long goodsId) {
        Member member = memberService.findMemberById(memberId);
        Goods goods = goodsService.findGoodsById(goodsId);
        boolean isInWishList = wishListService.existsWishItem(member, goods);
        return new ExistsInWishListResponse(isInWishList);
    }
}
