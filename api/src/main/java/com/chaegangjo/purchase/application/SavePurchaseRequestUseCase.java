package com.chaegangjo.purchase.application;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.purchase.dto.request.SavePurchaseRequestRequest;
import com.chaegangjo.purchase.dto.response.PurchaseRequestInfo;
import com.chaegangjo.purchase.service.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class SavePurchaseRequestUseCase {

    private final MemberService memberService;
    private final PurchaseRequestService purchaseRequestService;

    @Transactional
    public PurchaseRequestInfo execute(SavePurchaseRequestRequest request, Long requesterId) {
        Member requester = memberService.findMemberById(requesterId);
        var purchaseRequest = purchaseRequestService.save(request.toEntity(requester));
        return PurchaseRequestInfo.from(purchaseRequest);
    }
}
