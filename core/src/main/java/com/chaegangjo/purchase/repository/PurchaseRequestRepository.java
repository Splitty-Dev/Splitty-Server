package com.chaegangjo.purchase.repository;

import com.chaegangjo.purchase.domain.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long>,
        PurchaseRequestCustomRepository {
}
