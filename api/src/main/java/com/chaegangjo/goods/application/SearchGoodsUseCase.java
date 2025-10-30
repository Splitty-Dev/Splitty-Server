package com.chaegangjo.goods.application;

import static com.chaegangjo.paging.PageProperties.GOODS_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.member.service.SearchHistoryService;
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

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final SearchHistoryService searchHistoryService;
    
    public CursorPageResponse<List<GoodsInfo>> execute(Long memberId, String keyword, Long cursorId, LocalDateTime cursorCreatedAt) {
        Member member = memberService.getMemberById(memberId);
        searchHistoryService.saveSearchHistory(member, keyword);

        Slice<Goods> goods = goodsService.findAllByKeywordAndCursor(
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
