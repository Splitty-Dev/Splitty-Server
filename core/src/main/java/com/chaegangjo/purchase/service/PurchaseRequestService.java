package com.chaegangjo.purchase.service;

import com.chaegangjo.exception.PurchaseRequestException;
import com.chaegangjo.exception.errorcode.PurchaseRequestErrorCode;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.repository.PurchaseRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    @Transactional
    public PurchaseRequest save(PurchaseRequest purchaseRequest) {
        return purchaseRequestRepository.save(purchaseRequest);
    }

    public PurchaseRequest findById(Long id) {
        return purchaseRequestRepository.findByIdWithRequester(id)
                .orElseThrow(() -> new PurchaseRequestException(PurchaseRequestErrorCode.PURCHASE_REQUEST_NOT_FOUND));
    }

    public Slice<PurchaseRequest> findAllByCursor(CursorPage cursorPage) {
        return purchaseRequestRepository.findAllByCursor(cursorPage);
    }
}
