package com.chaegangjo.goods.repository;


import com.chaegangjo.goods.domain.Goods;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsCustomRepository {
//    List<Goods> findAllByIdInAndCategoryIdOrderByIdDesc(List<Long> ids, Long categoryId);
    List<Goods> findAllByIdIn(List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from Goods g where g.id = :id")
    Optional<Goods> findByIdForUpdate(@Param("id") Long id);
}
