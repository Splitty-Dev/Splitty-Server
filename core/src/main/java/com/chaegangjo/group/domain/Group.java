package com.chaegangjo.group.domain;

import com.chaegangjo.common.entity.BaseEntity;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "small_group")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_id")
    private Member host;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, unique = true, length = 8)
    private String joinCode;

    @Column(nullable = false)
    private int maxMemberCount;

    @Column(nullable = false)
    private int currentMemberCount;

    @Builder
    public Group(Member host, String name, String description, String joinCode, int maxMemberCount) {
        this.host = host;
        this.name = name;
        this.description = description;
        this.joinCode = joinCode;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = 0;
    }

    public boolean isFull() {
        return currentMemberCount >= maxMemberCount;
    }

    public void incrementMemberCount() {
        this.currentMemberCount++;
    }
}
