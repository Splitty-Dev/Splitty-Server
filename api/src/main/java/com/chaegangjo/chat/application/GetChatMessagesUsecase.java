package com.chaegangjo.chat.application;

import static com.chaegangjo.paging.PageProperties.CHAT_MESSAGE_PAGE_SIZE;

import com.chaegangjo.chat.dto.ChatMessageInfo;
import com.chaegangjo.chat.dto.ChatMemberInfo;
import com.chaegangjo.chat.dto.ChatMessagesInfo;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetChatMessagesUsecase {

    private final ChatMemberService chatMemberService;
    private final ChatMessageService chatMessageService;

    public CursorPageResponse<ChatMessagesInfo> execute(Long goodsId, Long cursorId, LocalDateTime createdAt) {
        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(goodsId);
        List<ChatMemberInfo> members = chatMembers.stream()
                .map(ChatMemberInfo::from).toList();

        IdCreatedAtCursorPage cursorPage = new IdCreatedAtCursorPage(CHAT_MESSAGE_PAGE_SIZE, cursorId, createdAt);
        Slice<ChatMessage> chatMessages = chatMessageService.findAllByGoodsIdAndCursor(goodsId, cursorPage);
        List<ChatMessageInfo> messages = chatMessages.getContent().stream()
                .map(ChatMessageInfo::from).toList();

        ChatMessagesInfo data = new ChatMessagesInfo(members, messages);

        IdCreatedAtNextCursor nextCursor = null;
        if (chatMessages.hasNext()) {
            ChatMessage last = chatMessages.getContent().getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        return CursorPageResponse.<ChatMessagesInfo>builder()
                .data(data)
                .hasNext(chatMessages.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
