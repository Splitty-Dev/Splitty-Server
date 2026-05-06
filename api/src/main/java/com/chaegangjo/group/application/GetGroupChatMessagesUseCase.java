package com.chaegangjo.group.application;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.GroupChatMember;
import com.chaegangjo.group.domain.GroupChatMessage;
import com.chaegangjo.group.dto.GroupChatMemberInfo;
import com.chaegangjo.group.dto.GroupChatMessageInfo;
import com.chaegangjo.group.dto.GroupChatMessagesInfo;
import com.chaegangjo.group.service.GroupChatMemberService;
import com.chaegangjo.group.service.GroupChatMessageService;
import com.chaegangjo.group.service.GroupChatRoomService;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.NOT_GROUP_MEMBER;
import static com.chaegangjo.paging.PageProperties.CHAT_MESSAGE_PAGE_SIZE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class GetGroupChatMessagesUseCase {

    private final GroupChatRoomService groupChatRoomService;
    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatMessageService groupChatMessageService;

    public CursorPageResponse<GroupChatMessagesInfo> execute(Long groupId, Long memberId, Long cursorId, LocalDateTime cursorCreatedAt) {
        Long chatRoomId = groupChatRoomService.findByGroupId(groupId).getId();

        boolean isMember = groupChatMemberService.findAllByChatRoomId(chatRoomId).stream()
                .anyMatch(m -> m.getMember().getId().equals(memberId));
        if (!isMember) {
            throw new GroupException(NOT_GROUP_MEMBER);
        }

        List<GroupChatMember> allMembers = groupChatMemberService.findAllByChatRoomId(chatRoomId);
        List<Long> chatMemberIds = allMembers.stream().map(GroupChatMember::getId).toList();

        IdCreatedAtCursorPage cursorPage = new IdCreatedAtCursorPage(CHAT_MESSAGE_PAGE_SIZE, cursorId, cursorCreatedAt);
        Slice<GroupChatMessage> messages = groupChatMessageService.findAllByCursor(chatMemberIds, cursorPage);

        IdCreatedAtNextCursor nextCursor = null;
        if (messages.hasNext()) {
            GroupChatMessage last = messages.getContent().getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        List<GroupChatMemberInfo> memberInfos = allMembers.stream().map(GroupChatMemberInfo::from).toList();
        List<GroupChatMessageInfo> messageInfos = messages.getContent().stream().map(GroupChatMessageInfo::from).toList();

        return CursorPageResponse.<GroupChatMessagesInfo>builder()
                .data(new GroupChatMessagesInfo(memberInfos, messageInfos))
                .hasNext(messages.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
