package com.chaegangjo.member.appllication;


import com.chaegangjo.member.dto.response.MemberInfoResponse;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetMemberInfoUseCase {

    private final MemberService memberService;

    public MemberInfoResponse execute(Long memberId) {

        return MemberInfoResponse.from(memberService.findMemberById(memberId));
    }

    public MemberInfoResponse execute(String email) {

        return MemberInfoResponse.from(memberService.findMemberByEmail(email));
    }
}
