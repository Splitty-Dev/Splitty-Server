package com.chaegangjo.goods.application;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.dto.DetailGoodsInfo;
import com.chaegangjo.goods.dto.GoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.logger.UserAction;
import com.chaegangjo.logger.UserActionLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GetGoodsUseCase {

    private final GoodsService goodsService;
    private final UserActionLogger userActionLogger;

    @Transactional
    public DetailGoodsInfo detail(Long userId, Long goodsId) {
        Goods goods = goodsService.findGoodsWithDetail(goodsId);
        //TODO: 조회수 중복 증가 방지 로직, 동시성 문제 처리
        goods.incrementViewCount();

        //로그 전송
        userActionLogger.logAction(userId, UserAction.VIEW, goodsId, goods.getCategory().getId(), goods.getUnitPrice());
        return DetailGoodsInfo.from(goods);
    }

    public GoodsInfo simple(Long goodsId) {
        Goods goods = goodsService.findGoodsById(goodsId);
        return GoodsInfo.from(goods);
    }
}
