package com.chaegangjo.member.appllication;


import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.wishlist.dto.WishListCursorPage;
import com.chaegangjo.wishlist.dto.request.GetWishList;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageInfo;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageInfo.NextCursor;
import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetWishListUsecase {

    private final WishListService wishListService;

    public WishListCursorPageInfo<List<GoodsInfo>> execute(Long memberId, GetWishList request) {

        WishListCursorPage cursorPage = new WishListCursorPage(request.cursorId(), request.cursorCreatedAt(), memberId);
        Slice<WishList> wishLists = wishListService.findWishListsByCursor(cursorPage);

        List<WishList> content = wishLists.getContent();


        if (content.isEmpty()) {
            return WishListCursorPageInfo.<List<GoodsInfo>>builder()
                    .data(Collections.EMPTY_LIST)
                    .build();
        }
        else {
            WishList last = content.getLast();

            List<GoodsInfo> data = content.stream()
                    .map(wishList ->
                            GoodsInfo.from(wishList.getGoods()))
                    .toList();

            return WishListCursorPageInfo.<List<GoodsInfo>>builder()
                    .data(data)
                    .hasNext(wishLists.hasNext())
                    .nextCursor(new NextCursor(last.getId(), last.getCreatedAt()))
                    .build();
        }
    }
}
