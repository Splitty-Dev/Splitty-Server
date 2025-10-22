package com.chaegangjo.wishlist.application;

import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteWishListItemUsecase {

    private final WishListService wishListService;

    public void execute(Long memberId, Long goodsId) {
        wishListService.deleteWishItem(memberId, goodsId);
    }
}
