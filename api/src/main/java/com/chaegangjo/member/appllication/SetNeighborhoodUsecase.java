package com.chaegangjo.member.appllication;


import com.chaegangjo.member.dto.request.SetNeighborhoodRequest;
import com.chaegangjo.member.dto.response.MemberInfoResponse;
import com.chaegangjo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SetNeighborhoodUsecase {

    private final MemberService memberService;

    public MemberInfoResponse execute(Long memberId, SetNeighborhoodRequest request) {

        return MemberInfoResponse.from(
                memberService.saveMemberLocation(memberId, request.latitude(), request.longitude()));
    }
}
