package com.chaegangjo.goods.application;

import com.chaegangjo.goods.domain.Category;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.GoodsImage;
import com.chaegangjo.goods.dto.DetailGoodsInfo;
import com.chaegangjo.goods.dto.request.SaveGoodsRequest;
import com.chaegangjo.goods.service.CategoryService;
import com.chaegangjo.goods.service.GoodsImageService;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.trade.domain.Trade;
import com.chaegangjo.trade.service.TradeMemberService;
import com.chaegangjo.trade.service.TradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class SaveGoodsUsecase {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final GoodsImageService goodsImageService;
    private final CategoryService categoryService;
    private final TradeService tradeService;
    private final TradeMemberService tradeMemberService;

    @Transactional
    public DetailGoodsInfo execute(SaveGoodsRequest request, Long sellerId) {
        Member seller = memberService.findMemberById(sellerId);
        Category category = categoryService.findCategoryById(request.categoryId());
        Goods goods = goodsService.saveGoods(request.toEntity(seller, category));
        Trade trade = tradeService.saveTrade(new Trade(goods));
        tradeMemberService.saveTradeMember(trade, seller, request.getMyQuantity());
        List<GoodsImage> goodsImages = goodsImageService.saveGoodsImages(goods, request.imageNames());
        return DetailGoodsInfo.of(goods, goodsImages);
    }
}
