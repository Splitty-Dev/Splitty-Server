package com.chaegangjo.goods.application;


import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.service.MemberService;
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
    private final MemberService memberService;

    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, Long categoryId, Long cursorId) {
        memberService.getMemberPoint(memberId);
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
}
