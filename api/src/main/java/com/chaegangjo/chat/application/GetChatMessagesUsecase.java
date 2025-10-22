package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.ChatMessageInfo;
import com.chaegangjo.chat.dto.ChatMemberInfo;
import com.chaegangjo.chat.dto.response.ChatMessageCursorPageResponse;
import com.chaegangjo.chat.dto.ChatMessagesInfo;
import com.chaegangjo.chat.dto.response.ChatMessageCursorPageResponse.NextCursor;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.service.ChatMessageService;
import com.chaegangjo.trade.service.TradeMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetChatMessagesUsecase {

    private final TradeMemberService tradeMemberService;
    private final ChatMessageService chatMessageService;

    public ChatMessageCursorPageResponse<ChatMessagesInfo> execute(Long tradeId, Long cursorId, LocalDateTime createdAt) {
        List<TradeMember> tradeMembers = tradeMemberService.findMembersByTradeId(tradeId);
        List<ChatMemberInfo> members = tradeMembers.stream()
                .map(ChatMemberInfo::from).toList();

        Slice<ChatMessage> chatMessages = chatMessageService.getChatMessages(tradeId, cursorId, createdAt);
        List<ChatMessageInfo> messages = chatMessages.getContent().stream()
                .map(ChatMessageInfo::from).toList();

        ChatMessagesInfo data = new ChatMessagesInfo(members, messages);

        NextCursor nextCursor = null;
        if (chatMessages.hasNext()) {
            ChatMessage last = chatMessages.getContent().getLast();
            nextCursor = new NextCursor(last.getId(), last.getCreatedAt());
        }

        return ChatMessageCursorPageResponse.<ChatMessagesInfo>builder()
                .data(data)
                .hasNext(chatMessages.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
