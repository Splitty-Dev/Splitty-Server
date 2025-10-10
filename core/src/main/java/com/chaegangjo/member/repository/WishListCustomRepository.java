package com.chaegangjo.member.repository;


import com.chaegangjo.member.domain.WishList;
import com.chaegangjo.member.dto.WishListCursorPage;
import org.springframework.data.domain.Slice;

public interface WishListCustomRepository {

    Slice<WishList> findWishListsByCursor(WishListCursorPage page);
}