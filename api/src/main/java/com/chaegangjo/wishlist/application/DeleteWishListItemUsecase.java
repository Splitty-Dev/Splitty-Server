package com.chaegangjo.wishlist.application;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteWishListItemUseCase {

    private final GoodsService goodsService;
    private final WishListService wishListService;
    private final MemberService memberService;

    public void execute(Long memberId, Long goodsId) {
        Member member = memberService.findMemberById(memberId);
        Goods goods = goodsService.findGoodsById(goodsId);
        goods.decrementTotalWishlist();
        wishListService.deleteWishItem(member, goods);
    }
}
