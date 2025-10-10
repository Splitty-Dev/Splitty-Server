package com.chaegangjo.member.service;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.WishList;
import com.chaegangjo.member.dto.WishListCursorPage;
import com.chaegangjo.member.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.MemberErrorCode.WISH_ALREADY_EXISTS;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public Slice<WishList> findWishListsByCursor(WishListCursorPage cursorPage) {
        return wishListRepository.findWishListsByCursor(cursorPage);
    }

    @Transactional
    public void saveWishList(Member member, Goods goods) {
        if (wishListRepository.existsByMemberAndGoods(member, goods)) throw new MemberException(WISH_ALREADY_EXISTS);
        wishListRepository.save(new WishList(member, goods));
    }
}
