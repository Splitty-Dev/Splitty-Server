package com.chaegangjo.wishlist.domain;

import com.chaegangjo.entity.BaseEntity;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WishList extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @CreatedDate
    private LocalDateTime createdAt;

    public WishList(Member member, Goods goods) {
        this.member = member;
        this.goods = goods;
    }
}