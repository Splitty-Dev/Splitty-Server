package com.chaegangjo.goods.domain;


import com.chaegangjo.entity.BaseEntity;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.member.domain.Member;
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

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeStatus status;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int unitPrice;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int totalQuantity;

    @Column(nullable = false)
    private int leftQuantity;

    @Column(nullable = false)
    private int currParticipants;

    private String preferredLocation;

    private String mainImageName;

    private int totalWishlist;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsImage> images = new ArrayList<>();

    @Builder
    public Goods(Member seller, Category category, String name, int totalPrice, int totalQuantity, String description, int leftQuantity, String preferredLocation, String mainImageName) {
        this.seller = seller;
        this.category = category;
        this.neighName = seller.getNeighName();
        this.description = description;
        this.leftQuantity = leftQuantity;
        this.preferredLocation = preferredLocation;
        this.name = name;
        this.status = TradeStatus.OPEN;
        this.totalPrice = totalPrice;
        this.unitPrice = calculateUnitPrice(totalPrice, totalQuantity);
        this.viewCount = 0;
        this.totalQuantity = totalQuantity;
        this.currParticipants = 1;
        this.totalWishlist = 0;
        this.mainImageName = mainImageName;
    }

    public int calculateUnitPrice(int totalPrice, int totalQuantity) {
        return (int) Math.ceil((double) totalPrice / totalQuantity);
    }

    public boolean canBuy(int quantity) {
        return this.leftQuantity >= quantity;
    }

    public void joinTrade(int quantity) {
        this.leftQuantity -= quantity;
        this.currParticipants -= 1;
    }

    public boolean isOpened() {
        return status == TradeStatus.OPEN;
    }

    public void addImage(GoodsImage image) {
        this.images.add(image);
        image.setGoods(this);
    }

    public void incrementTotalWishlist() {
        this.totalWishlist++;
    }

    public void decrementTotalWishlist() {
        if (this.totalWishlist > 0) {
            this.totalWishlist--;
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void changeTradeStatus(TradeStatus status) {
        this.status = status;
    }
}