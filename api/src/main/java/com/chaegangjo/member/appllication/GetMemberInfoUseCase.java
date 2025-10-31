package com.chaegangjo.member.appllication;


import com.chaegangjo.member.dto.MemberInfo;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetMemberInfoUseCase {

    private final MemberService memberService;

    public MemberInfo execute(Long memberId) {

        return MemberInfo.from(memberService.findMemberById(memberId));
    }

    public MemberInfo execute(String email) {

        return MemberInfo.from(memberService.findMemberByEmail(email));
    }
}
