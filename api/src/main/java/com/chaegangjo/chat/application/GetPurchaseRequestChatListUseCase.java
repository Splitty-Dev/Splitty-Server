package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.PurchaseRequestChatInfo;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import com.chaegangjo.purchase.service.PurchaseRequestChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class GetPurchaseRequestChatListUseCase {

    private final PurchaseRequestChatService purchaseRequestChatService;

    public List<PurchaseRequestChatInfo> execute(Long memberId) {
        List<PurchaseRequestChatRoom> chatRooms = purchaseRequestChatService.findAllChatRoomsByMemberId(memberId);

        List<Long> chatRoomIds = chatRooms.stream().map(PurchaseRequestChatRoom::getId).toList();
        List<PurchaseRequestChatMessage> lastMessages = purchaseRequestChatService.getLastMessages(chatRoomIds);

        List<PurchaseRequestChatInfo> data = chatRooms.stream()
                .map(room -> {
                    PurchaseRequestChatMessage lastMessage = lastMessages.stream()
                            .filter(m -> m.getChatRoom().getId().equals(room.getId()))
                            .findFirst()
                            .orElse(null);
                    return PurchaseRequestChatInfo.of(room, lastMessage, memberId);
                })
                .collect(Collectors.toList());

        data.sort((c1, c2) -> {
            LocalDateTime t1 = c1.updatedAt();
            LocalDateTime t2 = c2.updatedAt();
            if (t1 == null && t2 == null) return 0;
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            return t2.compareTo(t1);
        });

        return data;
    }
}
