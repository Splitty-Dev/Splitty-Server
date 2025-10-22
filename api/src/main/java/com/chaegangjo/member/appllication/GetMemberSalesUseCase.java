package com.chaegangjo.member.appllication;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.response.GoodsInfo;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

@RequiredArgsConstructor
@Component
public class GetMemberSalesUseCase {

    private final GoodsService goodsService;

    public CursorPageResponse<List<GoodsInfo>> execute(Long sellerId, TradeStatus status, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<Goods> goods = goodsService.findAllByCursor(
                new IdCreatedAtCursorPage(GOODS_PAGE_SIZE, cursorId, cursorCreatedAt), sellerId, status);

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
