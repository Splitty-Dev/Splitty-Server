package com.chaegangjo.member.appllication;


import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveMyFcmTokenUseCase {

    private final MemberService memberService;

    public void execute(Long memberId, String fcmToken) {
        memberService.saveMyFcmToken(memberId, fcmToken);
    }
}
