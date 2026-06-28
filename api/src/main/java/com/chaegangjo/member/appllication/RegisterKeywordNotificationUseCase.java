package com.chaegangjo.member.appllication;

import com.chaegangjo.member.domain.KeywordNotification;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.dto.KeywordNotificationInfo;
import com.chaegangjo.member.dto.request.RegisterKeywordRequest;
import com.chaegangjo.member.service.KeywordNotificationService;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegisterKeywordNotificationUseCase {

    private final MemberService memberService;
    private final KeywordNotificationService keywordNotificationService;

    public KeywordNotificationInfo execute(Long memberId, RegisterKeywordRequest request) {
        Member member = memberService.findMemberById(memberId);
        KeywordNotification keywordNotification = keywordNotificationService.register(member, request.keyword());
        return KeywordNotificationInfo.from(keywordNotification);
    }
}
