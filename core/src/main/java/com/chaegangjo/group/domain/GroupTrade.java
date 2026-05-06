package com.chaegangjo.group.domain;

import com.chaegangjo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupTrade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int pricePerPerson;

    @OneToMany(mappedBy = "groupTrade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupTradeItem> items = new ArrayList<>();

    @Builder
    public GroupTrade(Group group, String name, int pricePerPerson) {
        this.group = group;
        this.name = name;
        this.pricePerPerson = pricePerPerson;
    }
}
