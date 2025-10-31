package com.chaegangjo.member.domain;

import com.chaegangjo.entity.BaseCreatedEntity;
import com.chaegangjo.firebase.FcmMessageTemplate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column
    private String imageName;

    public Notification(String title, String body, String imageName) {
        this.title = title;
        this.body = body;
        this.imageName = imageName;
    }

    public static Notification of(FcmMessageTemplate template, String imageName) {
        return new Notification(template.getTitle(), template.getBody(), imageName);
    }
}
