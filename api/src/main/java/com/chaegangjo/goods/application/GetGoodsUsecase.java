package com.chaegangjo.goods.application;


import com.chaegangjo.goods.dto.response.GoodsCursorPageResponse;
import com.chaegangjo.goods.dto.response.GoodsCursorPageResponse.NextCursor;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.response.GoodsInfoResponse;
import com.chaegangjo.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.chaegangjo.utils.PageProperties.GOODS_PAGE_SIZE;

@RequiredArgsConstructor
@Component
public class GetGoodsUsecase {

    private final GoodsService goodsService;

    public GoodsCursorPageResponse<List<GoodsInfoResponse>> execute(Long memberId, Long cursorId) {

        List<Goods> goods = goodsService.findAllByCursor(memberId, cursorId);

        if (goods.isEmpty()) {
            return GoodsCursorPageResponse.<List<GoodsInfoResponse>>builder()
                    .data(Collections.EMPTY_LIST)
                    .build();
        }

        Goods last = goods.getLast();

        List<GoodsInfoResponse> data = goods.stream().map(GoodsInfoResponse::from)
                .toList();

        boolean hasNext = goods.size() == GOODS_PAGE_SIZE;

        return GoodsCursorPageResponse.<List<GoodsInfoResponse>>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(new NextCursor(last.getId()))
                .build();
    }
}
