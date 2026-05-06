package com.chaegangjo.group.application;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.paging.NextCursor;
import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.GroupTrade;
import com.chaegangjo.group.dto.GroupTradeInfo;
import com.chaegangjo.group.service.GroupMemberService;
import com.chaegangjo.group.service.GroupTradeService;
import com.chaegangjo.paging.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.NOT_GROUP_MEMBER;

@RequiredArgsConstructor
@Component
public class GetGroupTradeListUseCase {

    private static final int GROUP_TRADE_PAGE_SIZE = 20;

    private final GroupTradeService groupTradeService;
    private final GroupMemberService groupMemberService;

    public CursorPageResponse<List<GroupTradeInfo>> execute(Long groupId, Long memberId, Long cursorId) {
        if (!groupMemberService.isMember(groupId, memberId)) {
            throw new GroupException(NOT_GROUP_MEMBER);
        }

        Slice<GroupTrade> trades = groupTradeService.findTradesByGroupId(
                groupId, new CursorPage(GROUP_TRADE_PAGE_SIZE, cursorId)
        );

        List<GroupTrade> content = trades.getContent();
        NextCursor nextCursor = null;
        if (trades.hasNext()) {
            nextCursor = new NextCursor(content.getLast().getId());
        }

        List<GroupTradeInfo> data = content.stream()
                .map(GroupTradeInfo::from)
                .toList();

        return CursorPageResponse.<List<GroupTradeInfo>>builder()
                .data(data)
                .hasNext(trades.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
