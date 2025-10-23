package com.chaegangjo.trade.service;

import com.chaegangjo.exception.TradeException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.trade.domain.Trade;
import com.chaegangjo.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.errorcode.TradeErrorCode.TRADE_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public Trade findTradeByGoodsId(Long goodsId) {
        return tradeRepository.findTradeByGoods_Id(goodsId).orElseThrow(() -> new TradeException(TRADE_NOT_FOUND));
    }

    public Trade findTradeByGoods(Goods goods) {
        return tradeRepository.findByGoods(goods).orElseThrow(() -> new TradeException(TRADE_NOT_FOUND));
    }
}
