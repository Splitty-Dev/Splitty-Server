package com.chaegangjo.group.service;

import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.Group;
import com.chaegangjo.group.domain.GroupChatRoom;
import com.chaegangjo.group.repository.GroupChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.GROUP_CHAT_ROOM_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupChatRoomService {

    private final GroupChatRoomRepository groupChatRoomRepository;

    @Transactional
    public GroupChatRoom createChatRoom(Group group) {
        return groupChatRoomRepository.save(new GroupChatRoom(group));
    }

    public GroupChatRoom findByGroupId(Long groupId) {
        return groupChatRoomRepository.findByGroupId(groupId)
                .orElseThrow(() -> new GroupException(GROUP_CHAT_ROOM_NOT_FOUND));
    }
}
