package com.chaegangjo.security.oauth2.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final Long id;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String email, Long id) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.id = id;
    }

    public static Collection<GrantedAuthority> getAuthorities(String authority) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> authority);
        return authorities;
    }
}
