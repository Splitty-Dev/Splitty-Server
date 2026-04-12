package com.chaegangjo.purchase.repository;

import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.purchase.domain.PurchaseRequest;
import org.springframework.data.domain.Slice;

public interface PurchaseRequestCustomRepository {

    Slice<PurchaseRequest> findAllByCursor(CursorPage cursorPage);

    java.util.Optional<PurchaseRequest> findByIdWithRequester(Long id);
}
