package com.chaegangjo.group.service;

import com.chaegangjo.group.domain.GroupChatMember;
import com.chaegangjo.group.domain.GroupChatMessage;
import com.chaegangjo.group.repository.GroupChatMessageRepository;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupChatMessageService {

    private final GroupChatMessageRepository groupChatMessageRepository;

    @Transactional
    public GroupChatMessage saveMessage(GroupChatMember groupChatMember, String message) {
        return groupChatMessageRepository.save(new GroupChatMessage(groupChatMember, message));
    }

    public Slice<GroupChatMessage> findAllByCursor(List<Long> chatMemberIds, IdCreatedAtCursorPage page) {
        return groupChatMessageRepository.findAllByCursor(chatMemberIds, page);
    }
}
