package com.chaegangjo.goods.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.enums.TradeStatus;
import com.chaegangjo.paging.IdCreatedAtCursorPage;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface GoodsCustomRepository {

    Optional<Goods> findGoodsWithDetail(Long id);
    Slice<Goods> findAllByCursor(IdCreatedAtCursorPage page, Long memberId, TradeStatus tradeStatus);
}
