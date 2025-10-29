package com.chaegangjo.trade.repository;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    
    Optional<Trade> findByGoods(Goods goods);
    Optional<Trade> findTradeByGoods_Id(Long goodsId);
}
