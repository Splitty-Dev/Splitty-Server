package com.chaegangjo.goods.application;

import com.chaegangjo.goods.dto.response.DetailGoodsInfo;
import com.chaegangjo.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetDetailGoodsUsecase {

    private final GoodsService goodsService;

    public DetailGoodsInfo execute(Long goodsId) {

        return DetailGoodsInfo.from(goodsService.findGoodsWishDetail(goodsId));
    }
}
