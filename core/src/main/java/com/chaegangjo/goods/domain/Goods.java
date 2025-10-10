package com.chaegangjo.goods.domain;


import com.chaegangjo.entity.BaseEntity;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Goods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 20)
    private String neighName;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeStatus status;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int leftQuantity;

    @Column(nullable = false)
    private int maxParticipants;

    @Column(nullable = false)
    private int currParticipants;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsImage> images;

    @Builder
    public Goods(Member seller, Category category, String neighName, String name, int totalPrice, int quantity, int maxParticipants) {
        this.seller = seller;
        this.category = category;
        this.neighName = neighName;
        this.name = name;
        this.status = TradeStatus.OPEN;
        this.totalPrice = totalPrice;
        this.viewCount = 0;
        this.quantity = quantity;
        this.maxParticipants = maxParticipants;
        this.currParticipants = 1;
    }
}