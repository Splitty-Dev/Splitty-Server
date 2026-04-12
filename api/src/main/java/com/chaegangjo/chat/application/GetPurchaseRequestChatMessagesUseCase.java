package com.chaegangjo.chat.application;

import com.chaegangjo.chat.dto.ChatMemberInfo;
import com.chaegangjo.chat.dto.PurchaseRequestChatMessageInfo;
import com.chaegangjo.chat.dto.PurchaseRequestChatMessagesInfo;
import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.exception.PurchaseRequestException;
import com.chaegangjo.exception.errorcode.PurchaseRequestErrorCode;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import com.chaegangjo.purchase.domain.PurchaseRequestChatMessage;
import com.chaegangjo.purchase.domain.PurchaseRequestChatRoom;
import com.chaegangjo.purchase.dto.response.PurchaseRequestInfo;
import com.chaegangjo.purchase.service.PurchaseRequestChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.chaegangjo.paging.PageProperties.CHAT_MESSAGE_PAGE_SIZE;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class GetPurchaseRequestChatMessagesUseCase {

    private final PurchaseRequestChatService purchaseRequestChatService;

    public CursorPageResponse<PurchaseRequestChatMessagesInfo> execute(Long chatRoomId, Long currentMemberId,
                                                                        Long cursorId, LocalDateTime cursorCreatedAt) {
        PurchaseRequestChatRoom chatRoom = purchaseRequestChatService.findChatRoomById(chatRoomId);

        Long requesterId = chatRoom.getPurchaseRequest().getRequester().getId();
        Long helperId = chatRoom.getHelper().getId();
        if (!currentMemberId.equals(requesterId) && !currentMemberId.equals(helperId)) {
            throw new PurchaseRequestException(PurchaseRequestErrorCode.UNAUTHORIZED_CHAT_ROOM);
        }

        Member partner = currentMemberId.equals(requesterId)
                ? chatRoom.getHelper()
                : chatRoom.getPurchaseRequest().getRequester();
        ChatMemberInfo partnerInfo = ChatMemberInfo.builder()
                .id(partner.getId())
                .username(partner.getUsername())
                .profileImageUrl(partner.getProfileImageUrl())
                .build();

        IdCreatedAtCursorPage cursorPage = new IdCreatedAtCursorPage(CHAT_MESSAGE_PAGE_SIZE, cursorId, cursorCreatedAt);
        Slice<PurchaseRequestChatMessage> messages = purchaseRequestChatService.findMessagesByCursor(chatRoomId, cursorPage);
        List<PurchaseRequestChatMessageInfo> messageInfos = messages.getContent().stream()
                .map(PurchaseRequestChatMessageInfo::from).toList();

        PurchaseRequestChatMessagesInfo data = new PurchaseRequestChatMessagesInfo(
                PurchaseRequestInfo.from(chatRoom.getPurchaseRequest()),
                partnerInfo,
                messageInfos
        );

        IdCreatedAtNextCursor nextCursor = null;
        if (messages.hasNext()) {
            PurchaseRequestChatMessage last = messages.getContent().getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getCreatedAt());
        }

        return CursorPageResponse.<PurchaseRequestChatMessagesInfo>builder()
                .data(data)
                .hasNext(messages.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
