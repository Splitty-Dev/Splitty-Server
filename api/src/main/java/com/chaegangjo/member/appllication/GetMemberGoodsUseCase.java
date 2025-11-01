package com.chaegangjo.member.appllication;

import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.CompletedGoodsInfo;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetMemberGoodsUseCase {

    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;

    public CursorPageResponse<List<? extends GoodsInfo>> sold(Long memberId, TradeStatus status, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<Goods> goods = goodsService.findSoldGoodsByCursor(
                new IdCreatedAtCursorPage(GOODS_PAGE_SIZE, cursorId, cursorCreatedAt), memberId, status);

        if (status == TradeStatus.COMPLETED) {
            return getPageResponseWithReviewed(goods, memberId);
        } else {
            return getPageResponse(goods, memberId);
        }
    }

    public CursorPageResponse<List<? extends GoodsInfo>> purchased(Long memberId, TradeStatus status, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<Goods> goods = goodsService.findPurchasedGoodsByCursor(
                new IdCreatedAtCursorPage(GOODS_PAGE_SIZE, cursorId, cursorCreatedAt), memberId, status);

        if (status == TradeStatus.COMPLETED) {
            return getPageResponseWithReviewed(goods, memberId);
        } else {
            return getPageResponse(goods, memberId);
        }
    }

    private CursorPageResponse<List<? extends GoodsInfo>> getPageResponse(Slice<Goods> goods, Long memberId) {
        List<Goods> content = goods.getContent();
        IdCreatedAtNextCursor nextCursor = null;
        if (goods.hasNext()) {
            Goods last = content.getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        List<GoodsInfo> data = content.stream().map(GoodsInfo::from).toList();

        return CursorPageResponse.<List<? extends GoodsInfo>>builder()
                .data(data)
                .hasNext(goods.hasNext())
                .nextCursor(nextCursor)
                .build();
    }

    private CursorPageResponse<List<? extends GoodsInfo>> getPageResponseWithReviewed(Slice<Goods> goods, Long memberId) {
        List<Goods> content = goods.getContent();
        IdCreatedAtNextCursor nextCursor = null;
        if (goods.hasNext()) {
            Goods last = content.getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        Map<Goods, Boolean> goodsReviewedMap = content.stream()
                .collect(Collectors.toMap(
                        g -> g,
                        g -> {
                            ChatMember chatMember = chatMemberService.findChatMemberByGoodsIdAndMemberId(g.getId(), memberId);
                            return chatMember.isReviewed();
                        }
                ));

        List<CompletedGoodsInfo> data = goodsReviewedMap.entrySet().stream()
                .map(entry -> CompletedGoodsInfo.of(entry.getKey(), entry.getValue()))
                .toList();

        return CursorPageResponse.<List<? extends GoodsInfo>>builder()
                .data(data)
                .hasNext(goods.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
