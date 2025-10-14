package com.chaegangjo.member.appllication;


import com.chaegangjo.goods.dto.response.GoodsInfoResponse;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.wishlist.dto.WishListCursorPage;
import com.chaegangjo.wishlist.dto.request.GetWishListRequest;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageResponse;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageResponse.NextCursor;
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

    public WishListCursorPageResponse<List<GoodsInfoResponse>> execute(Long memberId, GetWishListRequest request) {

        WishListCursorPage cursorPage = new WishListCursorPage(request.cursorId(), request.cursorCreatedAt(), memberId);
        Slice<WishList> wishLists = wishListService.findWishListsByCursor(cursorPage);

        List<WishList> content = wishLists.getContent();


        if (content.isEmpty()) {
            return WishListCursorPageResponse.<List<GoodsInfoResponse>>builder()
                    .data(Collections.EMPTY_LIST)
                    .build();
        }
        else {
            WishList last = content.getLast();

            List<GoodsInfoResponse> data = content.stream()
                    .map(wishList ->
                            GoodsInfoResponse.from(wishList.getGoods()))
                    .toList();

            return WishListCursorPageResponse.<List<GoodsInfoResponse>>builder()
                    .data(data)
                    .hasNext(wishLists.hasNext())
                    .nextCursor(new NextCursor(last.getId(), last.getCreatedAt()))
                    .build();
        }
    }
}
