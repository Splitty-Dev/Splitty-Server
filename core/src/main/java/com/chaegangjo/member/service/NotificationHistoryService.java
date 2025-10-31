package com.chaegangjo.member.service;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.domain.NotificationHistory;
import com.chaegangjo.member.repository.NotificationHistoryRepository;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationHistoryService {

    private final NotificationHistoryRepository notificationHistoryRepository;

    public Slice<NotificationHistory> findAllByCursor(IdCreatedAtCursorPage cursorPage, Long memberId) {
        return notificationHistoryRepository.findAllByCursor(cursorPage, memberId);
    }

    @Transactional
    public void saveNotificationHistory(Member member, Notification notification) {
        NotificationHistory notificationHistory = new NotificationHistory(member, notification);
        notificationHistoryRepository.save(notificationHistory);
    }
}
