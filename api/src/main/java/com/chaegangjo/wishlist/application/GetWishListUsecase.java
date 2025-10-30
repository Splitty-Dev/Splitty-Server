package com.chaegangjo.wishlist.application;


import static com.chaegangjo.paging.PageProperties.WISH_LIST_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.wishlist.service.WishListService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetWishListUseCase {

    private final WishListService wishListService;

    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<WishList> wishLists = wishListService.findAllByCursor(
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