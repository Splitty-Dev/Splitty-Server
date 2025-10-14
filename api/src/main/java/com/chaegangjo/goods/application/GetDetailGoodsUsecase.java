package com.chaegangjo.goods.application;

import com.chaegangjo.goods.dto.response.DetailGoodsInfoResponse;
import com.chaegangjo.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetDetailGoodsUsecase {

    private final GoodsService goodsService;

    public DetailGoodsInfoResponse execute(Long goodsId) {

        return DetailGoodsInfoResponse.from(goodsService.findGoodsWishDetail(goodsId));
    }
}
