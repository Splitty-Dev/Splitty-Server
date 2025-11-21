package com.chaegangjo.chat.application;

import static com.chaegangjo.paging.PageProperties.CHAT_MESSAGE_PAGE_SIZE;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.dto.ChatMemberInfo;
import com.chaegangjo.chat.dto.ChatMessageInfo;
import com.chaegangjo.chat.dto.ChatMessagesInfo;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class GetChatMessagesUseCase {

    private final ChatMemberService chatMemberService;
    private final ChatMessageService chatMessageService;

    public CursorPageResponse<ChatMessagesInfo> execute(Long goodsId, Long cursorId, LocalDateTime createdAt) {
        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(goodsId); //활성, 비활성 거래 회원 모두 조회
        List<ChatMemberInfo> members = chatMembers.stream().map(ChatMemberInfo::from).toList();
        List<Long> chatMemberIds = chatMembers.stream().map(ChatMember::getId).toList();

        IdCreatedAtCursorPage cursorPage = new IdCreatedAtCursorPage(CHAT_MESSAGE_PAGE_SIZE, cursorId, createdAt);
        Slice<ChatMessage> chatMessages = chatMessageService.findAllByGoodsIdAndCursor(chatMemberIds, cursorPage);
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
