package com.chaegangjo.review.domain;

import com.chaegangjo.common.entity.BaseCreatedEntity;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.chat.domain.ChatMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseCreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private Member reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private ChatMember reviewer;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private float rating;

    @Builder
    public Review(Member reviewee, ChatMember reviewer, String content, float rating) {
        this.reviewee = reviewee;
        this.reviewer = reviewer;
        this.content = content;
        this.rating = rating;
    }
}