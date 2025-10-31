package com.chaegangjo.member.repository;


import com.chaegangjo.member.domain.NotificationHistory;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import org.springframework.data.domain.Slice;

public interface NotificationHistoryCustomRepository {

    Slice<NotificationHistory> findAllByCursor(IdCreatedAtCursorPage page, Long memberId);
}