package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupChatMessage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface GroupChatMessageCustomRepository {

    Slice<GroupChatMessage> findAllByCursor(List<Long> chatMemberIds, IdCreatedAtCursorPage page);
}
