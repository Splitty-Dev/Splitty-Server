package com.chaegangjo.goods.application;


import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.paging.NextCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

@RequiredArgsConstructor
@Component
public class GetGoodsUsecase {

    private final GoodsService goodsService;

    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, Long cursorId) {

        List<Goods> goods = goodsService.findSoldGoodsByCursor(memberId, cursorId);

        if (goods.isEmpty()) {
            return CursorPageResponse.<List<GoodsInfo>>builder()
                    .data(Collections.EMPTY_LIST)
                    .build();
        }

        Goods last = goods.getLast();
        List<GoodsInfo> data = goods.stream().map(GoodsInfo::from)
                .toList();

        boolean hasNext = false;
        NextCursor nextCursor = null;
        if (goods.size() == GOODS_PAGE_SIZE) {
            hasNext = true;
            nextCursor = new NextCursor(last.getId());
        }

        return CursorPageResponse.<List<GoodsInfo>>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }
}
