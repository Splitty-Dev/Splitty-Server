package com.chaegangjo.goods.application;


import com.chaegangjo.pagination.CursorPageInfo;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.goods.dto.response.GoodsNextCursor;
import com.chaegangjo.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chaegangjo.pagination.PageProperties.GOODS_PAGE_SIZE;

@RequiredArgsConstructor
@Component
public class GetGoodsUsecase {

    private final GoodsService goodsService;

    public CursorPageInfo execute(Long memberId, Long cursorId) {

        List<Goods> goods = goodsService.getGoods(memberId, cursorId);

        if (goods.isEmpty()) {
            return CursorPageInfo.builder()
                    .data(Collections.EMPTY_LIST)
                    .build();
        }

        Goods last = goods.getLast();

        List<GoodsInfo> data = goods.stream().map(GoodsInfo::from)
                .toList();

        boolean hasNext = goods.size() == GOODS_PAGE_SIZE;

        return CursorPageInfo.builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(new GoodsNextCursor(last.getId()))
                .build();
    }
}
