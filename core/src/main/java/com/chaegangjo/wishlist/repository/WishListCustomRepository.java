package com.chaegangjo.wishlist.repository;


import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.wishlist.dto.WishListCursorPage;
import org.springframework.data.domain.Slice;

public interface WishListCustomRepository {

    Slice<WishList> findAllByCursor(WishListCursorPage page);
}