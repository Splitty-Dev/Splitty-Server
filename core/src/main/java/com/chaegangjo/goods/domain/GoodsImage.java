package com.chaegangjo.goods.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GoodsImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goods_id")
    private Goods goods;

    @Column(nullable = false)
    private boolean isMain = false;

    @Column(nullable = false)
    private String imageUrl;
}
