package com.chaegangjo.trade.domain;

import com.chaegangjo.entity.BaseEntity;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TradeMember extends BaseEntity {

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

    public TradeMember(Goods goods, Member member, int quantity) {
        this.member = member;
        this.username = member.getUsername();
        this.goods = goods;
        this.quantity = quantity;
    }
}