package com.chaegangjo.wishlist.service;

import static com.chaegangjo.exception.errorcode.WishListErrorCode.WISH_ALREADY_EXISTS;
import static com.chaegangjo.exception.errorcode.WishListErrorCode.WISH_NOT_FOUND;

import com.chaegangjo.exception.WishListException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.wishlist.domain.WishList;
import com.chaegangjo.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public Slice<WishList> findWishListByCursor(IdCreatedAtCursorPage cursorPage, Long memberId) {
        return wishListRepository.findAllByCursor(cursorPage, memberId);
    }

    public boolean existsWishItem(Member member, Goods goods) {
        return wishListRepository.existsByMemberAndGoods(member, goods);
    }

    @Transactional
    public void saveWishItem(Member member, Goods goods) {
        if (wishListRepository.existsByMemberAndGoods(member, goods)) throw new WishListException(WISH_ALREADY_EXISTS);
        wishListRepository.save(new WishList(member, goods));
    }

    @Transactional
    public void deleteWishItem(Long memberId, Long goodsId) {
        if (!wishListRepository.existsByMember_IdAndGoods_Id(memberId, goodsId)) throw new WishListException(WISH_NOT_FOUND);
        wishListRepository.deleteWishListByMember_IdAndGoods_Id(memberId, goodsId);
    }

    @Transactional
    public void deleteWishItem(Member member, Goods goods) {
        if (!wishListRepository.existsByMemberAndGoods(member, goods)) throw new WishListException(WISH_NOT_FOUND);
        wishListRepository.deleteByMemberAndGoods(member, goods);
    }
}
