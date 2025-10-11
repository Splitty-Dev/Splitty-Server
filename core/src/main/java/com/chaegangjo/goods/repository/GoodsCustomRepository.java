package com.chaegangjo.goods.repository;

import com.chaegangjo.goods.domain.Goods;

import java.util.Optional;

public interface GoodsCustomRepository {

    Optional<Goods> findGoodsWithDetail(Long id);
}
