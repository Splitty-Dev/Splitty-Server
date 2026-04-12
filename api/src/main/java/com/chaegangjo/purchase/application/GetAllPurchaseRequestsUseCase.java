package com.chaegangjo.purchase.application;

import com.chaegangjo.dto.CursorPageResponse;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.NextCursor;
import com.chaegangjo.paging.PageProperties;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import com.chaegangjo.purchase.dto.response.PurchaseRequestSummaryInfo;
import com.chaegangjo.purchase.service.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class GetAllPurchaseRequestsUseCase {

    private static final int PURCHASE_REQUEST_PAGE_SIZE = 20;

    private final PurchaseRequestService purchaseRequestService;

    public CursorPageResponse<List<PurchaseRequestSummaryInfo>> execute(Long cursorId) {
        Slice<PurchaseRequest> slice = purchaseRequestService.findAllByCursor(
                new CursorPage(PURCHASE_REQUEST_PAGE_SIZE, cursorId));

        List<PurchaseRequestSummaryInfo> data = slice.getContent().stream()
                .map(PurchaseRequestSummaryInfo::from)
                .toList();

        NextCursor nextCursor = null;
        if (slice.hasNext()) {
            nextCursor = new NextCursor(slice.getContent().getLast().getId());
        }

        return CursorPageResponse.<List<PurchaseRequestSummaryInfo>>builder()
                .data(data)
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
