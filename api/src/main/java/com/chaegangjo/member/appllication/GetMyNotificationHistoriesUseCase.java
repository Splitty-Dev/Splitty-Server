package com.chaegangjo.member.appllication;

import static com.chaegangjo.paging.PageProperties.NOTIFICATION_HISTORY_PAGE_SIZE;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.member.domain.NotificationHistory;
import com.chaegangjo.member.dto.NotificationHistoryInfo;
import com.chaegangjo.member.service.NotificationHistoryService;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.paging.IdCreatedAtNextCursor;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetMyNotificationHistoriesUseCase {

    private final NotificationHistoryService notificationHistoryService;

    public CursorPageResponse<List<NotificationHistoryInfo>> execute(Long memberId, Long cursorId, LocalDateTime cursorCreatedAt) {
        Slice<NotificationHistory> notificationHistories = notificationHistoryService.findAllByCursor(
                new IdCreatedAtCursorPage(NOTIFICATION_HISTORY_PAGE_SIZE, cursorId, cursorCreatedAt), memberId);

        List<NotificationHistory> content = notificationHistories.getContent();
        IdCreatedAtNextCursor nextCursor = null;
        if (notificationHistories.hasNext()) {
            NotificationHistory last = content.getLast();
            nextCursor = new IdCreatedAtNextCursor(last.getId(), last.getNotification().getCreatedAt());
        }

        List<NotificationHistoryInfo> data = content.stream()
                .map(NotificationHistoryInfo::from)
                .toList();

        return CursorPageResponse.<List<NotificationHistoryInfo>>builder()
                .data(data)
                .hasNext(notificationHistories.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
