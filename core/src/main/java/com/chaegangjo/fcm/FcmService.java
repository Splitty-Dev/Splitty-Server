package com.chaegangjo.fcm;

import static com.chaegangjo.firebase.FirebaseProperties.FCM_CHAT_BASE_PATH;
import static com.chaegangjo.firebase.FirebaseProperties.FCM_REDIS_KEY;

import com.chaegangjo.firebase.FcmMessageTemplate;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.redis.RedisUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmService {

    private final RedisUtil redisUtil;

    public void sendMessage(Long memberId, String title, String content, String redirectUrl) {
        String key = FCM_REDIS_KEY + memberId;
        Optional<Object> value = redisUtil.getValue(key);
        if (value.isEmpty()) {
            return;
        }

        String token = (String) value.get();
        Message message = Message.builder()
                .putData("redirectUrl", redirectUrl)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(content)
                        .build())
                .setToken(token)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {

        }
    }

    public void sendGoodsMessages(List<Long> memberIds, Goods goods, FcmMessageTemplate template) {
        memberIds.forEach(memberId -> {
                    sendMessage(memberId,
                            getTradeMessageTitle(goods.getName(), template.getTitle()), template.getBody(), getFullPath(template.getBashPath(), goods.getId()));
        });
    }

    public void sendChatMessages(List<Long> memberIds, String senderUsername, String content, Long goodsId) {
        memberIds.forEach(memberId -> {
            sendMessage(memberId, senderUsername, content, getFullPath(FCM_CHAT_BASE_PATH, goodsId));
        });
    }

    public void sendPurchaseRequestChatMessage(Long memberId, String senderUsername, String content, Long chatRoomId) {
        sendMessage(memberId, senderUsername, content, getFullPath(FCM_PURCHASE_REQUEST_CHAT_BASE_PATH, chatRoomId));
    }
    private String getTradeMessageTitle(String name, String message) {
        return "'" + name + "' " + message;
    }


    public String getFullPath(String basePath, Long id) {
        return basePath + id;
    }
}
