package com.chaegangjo.goods.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.paging.CursorPage;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Slice;

public interface GoodsCustomRepository {

    Optional<Goods> findGoodsWithDetail(Long id);
    Slice<Goods> findSoldGoodsByCursor(IdCreatedAtCursorPage page, Long memberId, TradeStatus tradeStatus);
    Slice<Goods> findPurchasedGoodsByCursor(IdCreatedAtCursorPage page, Long buyerId, TradeStatus status);
    Slice<Goods> findGoodsByCursor(CursorPage page, List<Long> goodsId, Long categoryId);
}
