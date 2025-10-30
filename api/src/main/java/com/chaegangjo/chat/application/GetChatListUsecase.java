package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.ChatInfo;
import com.chaegangjo.trade.domain.ChatMessage;
import com.chaegangjo.trade.domain.Trade;
import com.chaegangjo.trade.domain.TradeMember;
import com.chaegangjo.trade.service.ChatMessageService;
import com.chaegangjo.trade.service.TradeMemberService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetChatListUsecase {

    private final ChatMessageService chatMessageService;
    private final TradeMemberService tradeMemberService;

    public List<ChatInfo> execute(Long memberId) {
        List<TradeMember> tradeMembers = tradeMemberService.findTradeMembersByMemberId(memberId);
        List<Trade> trades = tradeMembers.stream()
                .map(TradeMember::getTrade)
                .toList();
        List<Long> tradeIds = trades.stream()
                .map(Trade::getId)
                .toList();
        Map<Long, ChatMessage> lastMessages = chatMessageService.getLastMessages(tradeIds); //tradeId, lastMessage

        List<ChatInfo> data = trades.stream()
                .map(trade -> {
                    ChatMessage lastMessage = lastMessages.get(trade.getId());
                    return ChatInfo.of(trade, lastMessage);
                })
                .collect(Collectors.toList());

        data.sort(
                (c1, c2) -> {
                    LocalDateTime m1 = c1.updatedAt();
                    LocalDateTime m2 = c2.updatedAt();
                    if (m1 == null && m2 == null) return 0;
                    if (m1 == null) return 1;
                    if (m2 == null) return -1;
                    return m2.compareTo(m1);
                }
        );

        return data;
    }
}
