package com.chaegangjo.wishlist.application;

import com.chaegangjo.chat.dto.request.SaveWishItemRequest;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveWishListItemUsecase {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final WishListService wishListService;

    public void execute(Long memberId, SaveWishItemRequest request) {
        Member member = memberService.findMemberById(memberId);
        Goods goods = goodsService.findGoodsById(request.goodsId());
        goods.incrementTotalWishlist();
        wishListService.saveWishItem(member, goods);
    }
}
