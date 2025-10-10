package com.chaegangjo.member.appllication;


import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.member.domain.WishList;
import com.chaegangjo.member.dto.WishListCursorPage;
import com.chaegangjo.member.dto.request.GetWishList;
import com.chaegangjo.member.dto.response.WishListNextCursor;
import com.chaegangjo.member.service.WishListService;
import com.chaegangjo.pagination.CursorPageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetWishListUsecase {

    private final WishListService wishListService;

    public CursorPageInfo<List<GoodsInfo>> execute(Long memberId, GetWishList request) {

        WishListCursorPage cursorPage = new WishListCursorPage(request.cursorId(), request.cursorCreatedAt(), memberId);
        Slice<WishList> wishLists = wishListService.findWishListsByCursor(cursorPage);

        List<WishList> content = wishLists.getContent();


        if (!content.isEmpty()) {
            WishList last = content.getLast();

            List<GoodsInfo> data = content.stream()
                    .map(wishList ->
                            GoodsInfo.from(wishList.getGoods()))
                    .toList();

            return CursorPageInfo.<List<GoodsInfo>>builder()
                    .data(data)
                    .hasNext(wishLists.hasNext())
                    .nextCursor(new WishListNextCursor(last.getId(), last.getCreatedAt()))
                    .build();
        }
        else {
            return CursorPageInfo.<List<GoodsInfo>>builder()
                    .hasNext(false)
                    .build();
        }

    }
}
