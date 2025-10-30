package com.chaegangjo.goods.service;


import static com.chaegangjo.exception.errorcode.GoodsErrorCode.GOODS_NOT_FOUND;

import com.chaegangjo.exception.GoodsException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.goods.repository.GoodsRepository;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import com.chaegangjo.redis.RedisProperties;
import com.chaegangjo.redis.RedisUtil;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final RedisUtil redisUtil;

    private final static int RESTRICT_DISTANCE = 3000000;

    public Slice<Goods> findAllByCursor(CursorPage cursorPage, Long memberId, Long categoryId) {
        List<Long> nearByIds = redisUtil.getNearByIds(memberId, RESTRICT_DISTANCE);
        nearByIds.sort(Comparator.reverseOrder());
        return goodsRepository.findAllByCursor(cursorPage, nearByIds, categoryId);
    }

    public Slice<Goods> findAllByKeywordAndCursor(IdCreatedAtCursorPage page, String keyword) {
        return goodsRepository.findAllByKeywordAndCursor(page, keyword);
    }

    public Slice<Goods> getSoldGoodsByCursor(IdCreatedAtCursorPage page, Long sellerId, TradeStatus status) {
        return goodsRepository.findSoldGoodsByCursor(page, sellerId, status);
    }

    public Slice<Goods> findPurchasedGoodsByCursor(IdCreatedAtCursorPage page, Long buyerId, TradeStatus status) {
        return goodsRepository.findPurchasedGoodsByCursor(page, buyerId, status);
    }

    public Goods findGoodsById(Long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
    }

    public Goods findGoodsWithDetail(Long goodsId) {
        return goodsRepository.findGoodsWithDetail(goodsId).orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
    }

    public Goods saveGoods(Goods goods) {
        Point memberPoint = redisUtil.getPoint(RedisProperties.MEMBER_KEY, goods.getSeller().getId());
        Goods newGoods = goodsRepository.save(goods);
        redisUtil.saveLocation(RedisProperties.GOODS_KEY, newGoods.getId(), memberPoint);
        return newGoods;
    }
}