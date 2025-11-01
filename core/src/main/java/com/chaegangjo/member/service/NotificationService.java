package com.chaegangjo.member.service;

import com.chaegangjo.firebase.FcmMessageTemplate;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification saveNotification(FcmMessageTemplate template, Goods goods) {
        Notification notification = Notification.of(template, goods);
        return notificationRepository.save(notification);
    }
}
