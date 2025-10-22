package com.chaegangjo.wishlist.repository;


import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import org.springframework.data.domain.Slice;

public interface WishListCustomRepository {

    Slice<WishList> findAllByCursor(IdCreatedAtCursorPage page, Long memberId);
}