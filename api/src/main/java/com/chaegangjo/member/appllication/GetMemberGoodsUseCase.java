package com.chaegangjo.member.appllication;

import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.enums.TradeStatus;
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
public class GetMemberGoodsUseCase {

    private final GoodsService goodsService;

    public CursorPageResponse<List<GoodsInfo>> purchased(Long sellerId, TradeStatus status, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<Goods> goods = goodsService.findPurchasedGoodsByCursor(
                new IdCreatedAtCursorPage(GOODS_PAGE_SIZE, cursorId, cursorCreatedAt), sellerId, status);

        return getListCursorPageResponse(goods);
    }

    public CursorPageResponse<List<GoodsInfo>> sold(Long sellerId, TradeStatus status, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<Goods> goods = goodsService.getSoldGoodsByCursor(
                new IdCreatedAtCursorPage(GOODS_PAGE_SIZE, cursorId, cursorCreatedAt), sellerId, status);

        return getListCursorPageResponse(goods);
    }

    private static CursorPageResponse<List<GoodsInfo>> getListCursorPageResponse(Slice<Goods> goods) {
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
