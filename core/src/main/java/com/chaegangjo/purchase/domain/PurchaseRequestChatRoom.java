package com.chaegangjo.purchase.domain;

import com.chaegangjo.common.entity.BaseEntity;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PurchaseRequestChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_request_id")
    private PurchaseRequest purchaseRequest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "helper_id")
    private Member helper;

    @Column(nullable = false)
    private boolean active = true;

    public PurchaseRequestChatRoom(PurchaseRequest purchaseRequest, Member helper) {
        this.purchaseRequest = purchaseRequest;
        this.helper = helper;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
