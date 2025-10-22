package com.chaegangjo.wishlist.application;


import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import com.chaegangjo.paging.NextCursor;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.chaegangjo.paging.PageProperties.WISH_LIST_PAGE_SIZE;

@RequiredArgsConstructor
@Component
public class GetWishListUsecase {

    private final WishListService wishListService;

    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<WishList> wishLists = wishListService.findWishListByCursor(
                new IdCreatedAtCursorPage(WISH_LIST_PAGE_SIZE, cursorId, cursorCreatedAt), memberId);

        List<WishList> content = wishLists.getContent();
        IdCreatedAtNextCursor nextCursor = null;
        if (wishLists.hasNext()) {
            WishList last = content.getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        List<GoodsInfo> data = content.stream()
                .map(wishList ->
                        GoodsInfo.from(wishList.getGoods()))
                .toList();

        return CursorPageResponse.<List<GoodsInfo>>builder()
                .data(data)
                .hasNext(wishLists.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
