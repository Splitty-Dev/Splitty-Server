package com.chaegangjo.trade.application;

import com.chaegangjo.chat.domain.ChatMember;
import com.chaegangjo.chat.service.ChatMemberService;
import com.chaegangjo.fcm.FcmService;
import com.chaegangjo.firebase.FcmMessageTemplate;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.member.service.NotificationHistoryService;
import com.chaegangjo.member.service.NotificationService;
import com.chaegangjo.trade.dto.ChangeTradeStatusRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChangeTradeStatusUseCase {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;
    private final FcmService fcmService;
    private final NotificationService notificationService;
    private final NotificationHistoryService notificationHistoryService;

    @Transactional
    public void execute(Long memberId, ChangeTradeStatusRequest request) {
        Member member = memberService.findMemberById(memberId);
        Goods goods = goodsService.findGoodsById(request.goodsId());
        chatMemberService.findChatMemberByGoodsAndMember(goods, member);
        goods.changeTradeStatus(request.tradeStatus());

        //모집완료/거래완료 알림 전송 및 알림 기록 저장
        FcmMessageTemplate template;
        if (request.tradeStatus() == TradeStatus.COMPLETED) {
            template = FcmMessageTemplate.TRADE_COMPLETED;
        } else if (request.tradeStatus() == TradeStatus.CLOSED) {
            template = FcmMessageTemplate.TRADE_CLOSED;
        } else {
            return;
        }

        Notification notification = notificationService.saveNotification(template, goods);

        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(request.goodsId());
        List<Long> memberIds = chatMembers.stream()
                .filter(cm -> !cm.getMember().getId().equals(memberId))
                .map(cm -> {
                    Member participant = cm.getMember();
                    notificationHistoryService.saveNotificationHistory(participant, notification);
                    return participant.getId();
        }).toList();

        fcmService.sendGoodsMessages(memberIds, goods.getName(), template);
    }
}
