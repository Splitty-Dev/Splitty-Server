package com.chaegangjo.member.domain;


import com.chaegangjo.entity.BaseEntity;
import com.chaegangjo.member.enums.Role;
import com.chaegangjo.member.enums.SocialType;
import com.chaegangjo.member.utils.RandomUsername;
import com.chaegangjo.wishlist.domain.WishList;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, length=60)
    private String username;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private int reviewCount;

    @Column(length = 20)
    private String neighName;

    private Double latitude;

    private Double longitude;

    private String profileImageUrl;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    @Builder
    public Member(String email, String socialId, SocialType socialType, Role role, String profileImageUrl) {
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
        this.rating = 0;
        this.reviewCount = 0;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
        this.username = RandomUsername.getRandomUsername();
    }

    public void setLocation(String adminDong, Point point) {
        this.neighName = adminDong;
        this.latitude = point.getY();
        this.longitude = point.getX();
    }

    public void setLocation(Point point) {
        this.latitude = point.getY();
        this.longitude = point.getX();
    }

    public void calculateRating(float newRating) {
        float totalRating = rating * reviewCount;
        totalRating += newRating;
        reviewCount += 1;
        rating = totalRating / reviewCount;
    }
}