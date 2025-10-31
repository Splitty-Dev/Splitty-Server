package com.chaegangjo.goods.repository;


import com.chaegangjo.goods.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsCustomRepository {
//    List<Goods> findAllByIdInAndCategoryIdOrderByIdDesc(List<Long> ids, Long categoryId);
}
