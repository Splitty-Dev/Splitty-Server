package com.chaegangjo.member.domain;

import com.chaegangjo.common.entity.BaseCreatedEntity;
import com.chaegangjo.goods.domain.Goods;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String body;

    @ManyToOne(cascade = CascadeType.ALL)
    private Goods goods;

    public Notification(String title, String body, Goods goods) {
        this.title = title;
        this.body = body;
        this.goods = goods;
    }

//    public static Notification of(FcmMessageTemplate template, Goods goods) {
//        return new Notification(template.getTitle(), template.getBody(), goods);
//    }
}
