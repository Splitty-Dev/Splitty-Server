package com.chaegangjo.goods.application;

import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SearchGoodsUseCase {

    private final GoodsService goodsService;
    
    public CursorPageResponse<List<GoodsInfo>> execute(String keyword, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<Goods> goods = goodsService.getAllByKeyword(
                new IdCreatedAtCursorPage(GOODS_PAGE_SIZE, cursorId, cursorCreatedAt), keyword);

        List<Goods> content = goods.getContent();
        IdCreatedAtNextCursor nextCursor = null;
        if (goods.hasNext()) {
            Goods last = content.getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
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
}
