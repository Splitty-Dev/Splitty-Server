package com.chaegangjo.trade.application;

import static com.chaegangjo.chat.enums.TradeRole.BUYER;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.ALREADY_JOINED;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.INSUFFICIENT_STOCK;
import static com.chaegangjo.exception.errorcode.TradeErrorCode.TRADE_NOT_OPENED;

import com.chaegangjo.exception.TradeException;
import com.chaegangjo.fcm.FcmService;
import com.chaegangjo.firebase.FcmMessageTemplate;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.member.service.NotificationService;
import com.chaegangjo.trade.domain.MessageType;
import com.chaegangjo.trade.dto.request.JoinTradeRequest;
import com.chaegangjo.trade.service.ChatMessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class JoinTradeUseCase {

    private final GoodsService goodsService;
    private final ChatMemberService chatMemberService;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;
    private final FcmService fcmService;
    private final NotificationService notificationService;
    private final NotificationHistoryService notificationHistoryService;

    @Transactional
    public void execute(JoinTradeRequest request, Long buyerId) {
        Goods goods = goodsService.findGoodsById(request.goodsId());
        if (!goods.isOpened()) {
            throw new TradeException(TRADE_NOT_OPENED);
        }
        if (goods.canBuy(request.quantity())) {
            goods.joinTrade(request.quantity());
        } else {
            throw new TradeException(INSUFFICIENT_STOCK);
        }

        Member buyer = memberService.findMemberById(buyerId);
        if (chatMemberService.existsChatMemberByGoodsAndMember(goods, buyer)) {
            throw new TradeException(ALREADY_JOINED);
        }
        ChatMember chatMember = chatMemberService.saveChatMember(goods, buyer, request.quantity(), BUYER);
        chatMessageService.saveChatMessage(chatMember, MessageType.ENTER); //채팅방 입장 메시지 저장

        //거래 참여 알림 전송 및 알림 기록 저장
        FcmMessageTemplate template = NEW_PARTICIPANT;
        Notification notification = notificationService.saveNotification(template, goods);

        List<ChatMember> chatMembers = chatMemberService.findAllByGoodsId(request.goodsId());
        List<Long> memberIds = chatMembers.stream()
                .filter(cm -> !cm.getMember().getId().equals(buyerId))
                .map(cm -> {
                    Member participant = cm.getMember();
                    notificationHistoryService.saveNotificationHistory(participant, notification);
                    return participant.getId();
        }).toList();

        fcmService.sendGoodsMessages(memberIds, goods.getName(), template);
    }
}
