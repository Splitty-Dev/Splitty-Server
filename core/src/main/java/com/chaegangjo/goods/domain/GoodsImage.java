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
    private String imageName;

    public GoodsImage(Goods goods, String imageName) {
        this.goods = goods;
        this.imageName = imageName;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
