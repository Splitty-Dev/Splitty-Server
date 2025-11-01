package com.chaegangjo.goods.application;


import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.openfeign.api.RecommendationOpenFeign;
import com.chaegangjo.openfeign.dto.RecommendGoodsRequest;
import com.chaegangjo.openfeign.dto.RecommendGoodsResponse;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.NextCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetAllGoodsUseCase {

    private final GoodsService goodsService;
    private final RecommendationOpenFeign recommendationOpenFeign;

    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, Long categoryId, Long cursorId) {
        Slice<Goods> goods = goodsService.findAllByCursor(new CursorPage(GOODS_PAGE_SIZE, cursorId), memberId, categoryId);

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

    public CursorPageResponse<List<GoodsInfo>> executeWithRecommendation(Long memberId, Long categoryId, Long rank) { //cursorId는 rank와 같음
        if (rank == null || rank == 0L) {
            rank  = 1L;
        }

        List<Long> nearByIds = goodsService.getNearByIds(memberId, categoryId);
        RecommendGoodsRequest request = new RecommendGoodsRequest(memberId.toString(), GOODS_PAGE_SIZE, nearByIds, rank);
        RecommendGoodsResponse response = recommendationOpenFeign.recommendGoods(request);

        List<GoodsInfo> data = response.items().stream()
                .map(item -> {
                    Long itemId = item.itemId();
                    if (!goodsService.existsGoodsById(itemId)) {
                        return null;
                    } else {
                        return GoodsInfo.from(goodsService.findGoodsById(itemId));
                    }
                }).toList();

        NextCursor nextCursor = null;
        if (response.hasNext()) {
            nextCursor = new NextCursor(response.items().getLast().rank()+1); //다음 rank: 마지막 rank+1
        }

        return CursorPageResponse.<List<GoodsInfo>>builder()
                .data(data)
                .hasNext(response.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
