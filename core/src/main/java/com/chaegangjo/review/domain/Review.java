package com.chaegangjo.review.domain;


import com.chaegangjo.member.domain.Member;
import com.chaegangjo.trade.domain.TradeMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private Member reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private TradeMember reviewer;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private float rating;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}