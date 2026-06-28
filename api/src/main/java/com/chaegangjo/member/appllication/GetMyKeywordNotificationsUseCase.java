package com.chaegangjo.member.appllication;

import com.chaegangjo.member.dto.KeywordNotificationInfo;
import com.chaegangjo.member.service.KeywordNotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetMyKeywordNotificationsUseCase {

    private final KeywordNotificationService keywordNotificationService;

    public List<KeywordNotificationInfo> execute(Long memberId) {
        return keywordNotificationService.findAllByMemberId(memberId).stream()
                .map(KeywordNotificationInfo::from)
                .toList();
    }
}
