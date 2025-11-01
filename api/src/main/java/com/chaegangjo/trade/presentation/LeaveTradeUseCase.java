package com.chaegangjo.trade.presentation;

import static com.chaegangjo.firebase.FcmMessageTemplate.PARTICIPANT_LEAVE;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.enums.MessageType;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.chat.service.ChatMessageService;
import com.chaegangjo.fcm.FcmService;
import com.chaegangjo.firebase.FcmMessageTemplate;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.service.NotificationHistoryService;
import com.chaegangjo.member.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LeaveTradeUseCase {

    private final ChatMemberService chatMemberService;
    private final GoodsService goodsService;
    private final NotificationService notificationService;
    private final NotificationHistoryService notificationHistoryService;
    private final FcmService fcmService;
    private final ChatMessageService chatMessageService;

    public void execute(Long memberId, Long goodsId) {
        Goods goods = goodsService.findGoodsById(goodsId);
        goodsService.decrementCurrentParticipant(goods);
        ChatMember chatMember = chatMemberService.findChatMemberByGoodsIdAndMemberId(goodsId, memberId);
        chatMemberService.deactiveChatMember(memberId, goodsId);

        //거래 나가기 알림 전송 및 알림 기록 저장
        FcmMessageTemplate template = PARTICIPANT_LEAVE;
        Notification notification = notificationService.saveNotification(template, goods);

        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(goods.getId());
        List<Long> memberIds = chatMembers.stream()
                .filter(cm -> !cm.getMember().getId().equals(memberId))
                .map(cm -> {
                    Member participant = cm.getMember();
                    notificationHistoryService.saveNotificationHistory(participant, notification);
                    return participant.getId();
                }).toList();

        fcmService.sendGoodsMessages(memberIds, goods, template);

        //채팅 퇴장 메시지 저장
        chatMessageService.saveChatMessage(chatMember, MessageType.LEAVE); //채팅방 입장 메시지 저장

    }
}
