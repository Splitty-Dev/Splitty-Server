package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.ChatInfo;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.domain.ChatMessage;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.chat.service.ChatMemberService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetChatListUsecase {

    private final ChatMessageService chatMessageService;
    private final ChatMemberService chatMemberService;

    public List<ChatInfo> execute(Long memberId) {
        List<ChatMember> chatMembers = chatMemberService.findChatMembersByMemberId(memberId);
        List<Goods> goods = chatMembers.stream()
                .map(ChatMember::getGoods)
                .toList();
        List<Long> goodsId = goods.stream()
                .map(Goods::getId)
                .toList();
        List<ChatMessage> lastMessages = chatMessageService.getLastMessages(goodsId); //goodsId, lastMessage

        List<ChatInfo> data = goods.stream()
                .map(g -> {
                    ChatMessage lastMessage = lastMessages.stream()
                            .filter(m -> m.getChatMember().getGoods().getId().equals(g.getId()))
                            .findFirst()
                            .orElse(null);
                    return ChatInfo.of(g, lastMessage);
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
