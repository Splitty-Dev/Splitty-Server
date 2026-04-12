package com.chaegangjo.purchase.application;

import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.dto.response.PurchaseRequestInfo;
import com.chaegangjo.purchase.service.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class GetPurchaseRequestUseCase {

    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestInfo execute(Long purchaseRequestId) {
        PurchaseRequest purchaseRequest = purchaseRequestService.findById(purchaseRequestId);
        return PurchaseRequestInfo.from(purchaseRequest);
    }
}
