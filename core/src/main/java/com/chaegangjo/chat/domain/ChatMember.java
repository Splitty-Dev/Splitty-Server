package com.chaegangjo.chat.domain;

import com.chaegangjo.chat.enums.TradeRole;
import com.chaegangjo.common.entity.BaseEntity;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMember extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean reviewed = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeRole tradeRole = TradeRole.BUYER;

    public ChatMember(Goods goods, Member member, int quantity, TradeRole tradeRole) {
        this.member = member;
        this.username = member.getUsername();
        this.goods = goods;
        this.quantity = quantity;
        this.reviewed = false;
        this.tradeRole = tradeRole;
    }

    public void markAsReviewed() {
        this.reviewed = true;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}