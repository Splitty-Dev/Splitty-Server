package com.chaegangjo.group.domain;

import com.chaegangjo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupTradeItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_trade_id")
    private GroupTrade groupTrade;

    @Column(nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false)
    private int price;

    public GroupTradeItem(GroupTrade groupTrade, String itemName, int price) {
        this.groupTrade = groupTrade;
        this.itemName = itemName;
        this.price = price;
    }
}
