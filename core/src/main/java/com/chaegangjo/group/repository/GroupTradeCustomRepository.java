package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupTrade;
import com.chaegangjo.paging.CursorPage;
import org.springframework.data.domain.Slice;

public interface GroupTradeCustomRepository {

    Slice<GroupTrade> findAllByGroupIdAndCursor(Long groupId, CursorPage page);
}
