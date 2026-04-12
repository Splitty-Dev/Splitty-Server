package com.chaegangjo.purchase.domain;

import com.chaegangjo.common.entity.BaseEntity;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.purchase.enums.PurchaseRequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PurchaseRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id")
    private Member requester;

    @Column(nullable = false, length = 100)
    private String title;

    private LocalDateTime deadline;

    private String preferredLocation;

    @Column(nullable = false)
    private int fee;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int viewCount;

    private String imageName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseRequestStatus status;

    @Builder
    public PurchaseRequest(Member requester, String title, LocalDateTime deadline,
                           String preferredLocation, int fee, String content, String imageName) {
        this.requester = requester;
        this.title = title;
        this.deadline = deadline;
        this.preferredLocation = preferredLocation;
        this.fee = fee;
        this.content = content;
        this.imageName = imageName;
        this.viewCount = 0;
        this.status = PurchaseRequestStatus.OPEN;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void close() {
        this.status = PurchaseRequestStatus.CLOSED;
    }
}
