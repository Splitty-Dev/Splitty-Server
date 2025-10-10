package com.chaegangjo.goods.service;


import com.chaegangjo.exception.GoodsException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.repository.GoodsRepository;
import com.chaegangjo.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.chaegangjo.exception.GoodsErrorCode.GOODS_NOT_FOUND;
import static com.chaegangjo.pagination.PageProperties.GOODS_PAGE_SIZE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final RedisUtil redisUtil;

    private final static int RESTRICT_DISTANCE = 3000;

    public List<Goods> getGoods(Long memberId, Long cursorId) {
        List<Long> nearByIds = redisUtil.getNearByIds(memberId, RESTRICT_DISTANCE);
        nearByIds.sort(Comparator.reverseOrder());

        List<Long> ids = nearByIds.stream().filter(id -> id < cursorId)
                .limit(GOODS_PAGE_SIZE)
                .toList();

        return goodsRepository.findAllByIdInOrderByIdDesc(ids);
    }

    public Goods findGoodsById(Long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
    }
}