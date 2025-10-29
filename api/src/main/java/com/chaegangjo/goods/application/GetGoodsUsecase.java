package com.chaegangjo.goods.application;


import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.NextCursor;
import com.chaegangjo.paging.PageProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetGoodsUsecase {

    private final GoodsService goodsService;

    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, Long categoryId, Long cursorId) {
        Slice<Goods> goods = goodsService.findGoodsByCursor(new CursorPage(GOODS_PAGE_SIZE, cursorId), memberId, categoryId);

        List<Goods> content = goods.getContent();
        NextCursor nextCursor = null;
        if (goods.hasNext()) {
            Goods last = content.getLast();
            nextCursor = new NextCursor(last.getId());
        }

        List<GoodsInfo> data = content.stream()
                .map(GoodsInfo::from)
                .toList();

        return CursorPageResponse.<List<GoodsInfo>>builder()
                .data(data)
                .hasNext(goods.hasNext())
                .nextCursor(nextCursor)
                .build();
    }

//            if (goods.isEmpty()) {
//        return CursorPageResponse.<List<GoodsInfo>>builder()
//                .data(Collections.EMPTY_LIST)
//                .build();
//            }
//        Goods last = goods.getLast();
//        List<GoodsInfo> data = goods.stream().map(GoodsInfo::from)
//                .toList();
//
//        boolean hasNext = false;
//        NextCursor nextCursor = null;
//        if (goods.size() == GOODS_PAGE_SIZE) {
//            hasNext = true;
//            nextCursor = new NextCursor(last.getId());
//        }
}
