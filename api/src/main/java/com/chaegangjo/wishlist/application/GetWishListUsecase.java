package com.chaegangjo.wishlist.application;


import com.chaegangjo.goods.dto.response.GoodsInfoResponse;
import com.chaegangjo.paging.PageProperties;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageResponse;
import com.chaegangjo.wishlist.dto.response.WishListCursorPageResponse.NextCursor;
import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.chaegangjo.paging.PageProperties.WISH_LIST_PAGE_SIZE;

@RequiredArgsConstructor
@Component
public class GetWishListUsecase {

    private final WishListService wishListService;

    public WishListCursorPageResponse<List<GoodsInfoResponse>> execute(Long memberId, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<WishList> wishLists = wishListService.findWishListByCursor(
                new IdCreatedAtCursorPage(WISH_LIST_PAGE_SIZE, cursorId, cursorCreatedAt), memberId);

        List<WishList> content = wishLists.getContent();
        NextCursor nextCursor = null;
        if (wishLists.hasNext()) {
            WishList last = content.getLast();
            nextCursor = new NextCursor(last.getId(), last.getCreatedAt());
        }

        List<GoodsInfoResponse> data = content.stream()
                .map(wishList ->
                        GoodsInfoResponse.from(wishList.getGoods()))
                .toList();

        return WishListCursorPageResponse.<List<GoodsInfoResponse>>builder()
                .data(data)
                .hasNext(wishLists.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
