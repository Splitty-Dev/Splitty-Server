package com.chaegangjo.goods.repository;


import com.chaegangjo.goods.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsCustomRepository {
    List<Goods> findAllByIdInOrderByIdDesc(List<Long> ids);
}
