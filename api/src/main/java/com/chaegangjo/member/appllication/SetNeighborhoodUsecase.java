package com.chaegangjo.member.appllication;


import com.chaegangjo.member.dto.request.SetNeighborhoodRequest;
import com.chaegangjo.member.dto.response.MemberInfo;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SetNeighborhoodUsecase {

    private final MemberService memberService;

    public MemberInfo execute(Long memberId, SetNeighborhoodRequest request) {

        return MemberInfo.from(
                memberService.saveMemberLocation(memberId, request.latitude(), request.longitude()));
    }
}
